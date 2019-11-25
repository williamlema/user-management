package com.user.management.app.service.impl;

import com.user.management.app.constant.SupportedFiles;
import com.user.management.app.constant.TokenType;
import com.user.management.app.exception.InActiveTokenException;
import com.user.management.app.exception.NoPermissionsException;
import com.user.management.app.exception.NoSupportedFileException;
import com.user.management.app.model.dto.CredentialDto;
import com.user.management.app.model.dto.RegisterUserDto;
import com.user.management.app.model.entity.Rol;
import com.user.management.app.model.entity.Token;
import com.user.management.app.model.entity.User;
import com.user.management.app.repository.TokenRepository;
import com.user.management.app.service.api.IEmailService;
import com.user.management.app.service.api.IRegisterService;
import com.user.management.app.service.api.IRolService;
import com.user.management.app.service.api.IUserService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static com.user.management.app.constant.RolType.isAdmin;
import static com.user.management.app.constant.RolType.isAgent;
import static com.user.management.app.constant.SupportedFiles.isSupported;
import static com.user.management.app.util.TokenUtil.generateSafeToken;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;
import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * Implementation class for register operations
 *
 * @author <a href="weleonm@gmail.com">William Leon</a>
 * @version 1.0
 * @since 1.0
 */
@Service
public class RegisterServiceImpl implements IRegisterService {

    private final IEmailService emailService;

    private final IUserService userService;

    private final IRolService rolService;

    private final TokenRepository tokenRepository;

    private final Long DEFAULT_USER_ROL_ID = Long.valueOf(3);

    private final String SUBJECT = "Verificacion de cuenta";

    @Value( "${validate.address.url}" )
    private String VALIDATE_URL;

    @Autowired
    public RegisterServiceImpl(IEmailService emailService, IUserService userService, IRolService rolService, TokenRepository tokenRepository) {
        this.emailService = emailService;
        this.userService = userService;
        this.rolService = rolService;
        this.tokenRepository = tokenRepository;
    }

    /**
     * Create user and send email with verification token
     *
     * @param newUser
     */
    @Override
    public void registerNewUser(RegisterUserDto newUser) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = User.builder()
                .name(newUser.getName())
                .userName(newUser.getUserName())
                .password(encoder.encode(newUser.getPassword()))
                .email(newUser.getEmail())
                .phoneNumber(newUser.getPhoneNumber())
                .rol(rolService.get(DEFAULT_USER_ROL_ID))
                .activated(Boolean.FALSE)
                .build();

        User savedUser = userService.save(user);
        Token temporalToken = Token.builder()
                .token(generateSafeToken())
                .active(Boolean.TRUE)
                .type(TokenType.REGISTER.name())
                .user(savedUser)
                .build();
        Token generatedToken = tokenRepository.save(temporalToken);
        String message ="Abrir el siguiente link en el nevagador \n "
                .concat(VALIDATE_URL).concat(generatedToken.getToken());
        emailService.sendMail(newUser.getEmail(), SUBJECT, message);
    }

    /**
     * Validate verification
     *
     * @param token
     */
    @Override
    public void verificationUser(String token) {
        Token savedToken = tokenRepository.findFirstByTokenAndActive(token, Boolean.TRUE);
        if(isEmpty(savedToken)){
            throw new InActiveTokenException("Token inactivo");
        }
        userService.active(savedToken.getUser());
        savedToken.setActive(Boolean.FALSE);
        tokenRepository.save(savedToken);
    }

    /**
     * Created multiple user listed in file
     *
     * @param userFile
     */
    @Override
    public void bulkRegister(String authorization, MultipartFile userFile) throws IOException {
        Long rolId =tokenRepository.findFirstByTokenAndActive(authorization, Boolean.TRUE).getUser().getRol().getId();
        if(isAdmin(rolId) || isAgent(rolId)) {

            SupportedFiles fileType = isSupported(userFile.getOriginalFilename());
            List<User> userList = new ArrayList<>();
            switch (fileType){
                case CSV:
                    userList = getUserFromCSVFile(userFile);
                    break;
                case TVS:
                    new NoSupportedFileException("Archivo no soportado");
                    break;
                case XLSX:
                    userList = getUserFromExcelFile(userFile);
                    break;
                case XLS:
                    userList = getUserFromExcelFile(userFile);
                    break;
            }
            userService.saveAll(userList);
        }else {
            throw new NoPermissionsException("Usuario sin permisos para relaizar la operacion");
        }
    }

    /**
     * Validate if user complete registration process
     *
     * @param username
     */
    @Override
    public Integer validateUser(String username) {
        User savedUser = userService.getByUsername(username);
        if(isEmpty(savedUser)){
            return 1;
        }else
        if (!savedUser.isActivated() && isEmpty(savedUser.getPassword())){
            return 2;
        }else
        if (!savedUser.isActivated() && !isEmpty(savedUser.getPassword())){
            return 3;
        }
        return 0;
    }

    /**
     * Complete user registration
     *
     * @param credentialDto
     */
    @Override
    public void completeRegister(CredentialDto credentialDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User savedUser = userService.updatePassword(credentialDto.getUsername(),
                encoder.encode(credentialDto.getPassword()));
        Token temporalToken = Token.builder()
                .token(generateSafeToken())
                .active(Boolean.TRUE)
                .type(TokenType.REGISTER.name())
                .user(savedUser)
                .build();
        Token generatedToken = tokenRepository.save(temporalToken);
        String message ="Abrir el siguiente link en el nevagador \n "
                .concat(VALIDATE_URL).concat(generatedToken.getToken());
        emailService.sendMail(savedUser.getEmail(), SUBJECT, message);
    }

    private List<User> getUserFromCSVFile(MultipartFile userFile) throws IOException  {
        String line;
        InputStream is = userFile.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        List<User> userList = new ArrayList<>();
        Rol defaultRol = rolService.get(DEFAULT_USER_ROL_ID);
        br.readLine();//Cabeceras
        while ((line = br.readLine()) != null) {
            String data[] = line.split(",");
            userList.add(User.builder()
                    .name(data[0])
                    .userName(data[1])
                    .email(data[2])
                    .phoneNumber(data[3])
                    .activated(Boolean.FALSE)
                    .rol(defaultRol)
                    .build());
        }
        return userList.stream().distinct().collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing((c1)->c1.getUserName()))), ArrayList::new));
    }

    private List<User> getUserFromExcelFile(MultipartFile userFile) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(userFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);
        Rol defaultRol = rolService.get(DEFAULT_USER_ROL_ID);
        List<User> userList = new ArrayList<>();
        for(int i=1 ; i<worksheet.getPhysicalNumberOfRows() ;i++) {
            XSSFRow row = worksheet.getRow(i);
            if ( !isEmpty(row.getCell(0))
                    && !isEmpty(row.getCell(1))
                    && !isEmpty(row.getCell(2))
            ){
                userList.add(User.builder()
                        .name(row.getCell(0).getStringCellValue())
                        .userName(row.getCell(1).getStringCellValue())
                        .email(row.getCell(2).getStringCellValue())
                        .phoneNumber(!isEmpty(row.getCell(3))?row.getCell(3).toString():"")
                        .activated(Boolean.FALSE)
                        .rol(defaultRol)
                        .build());
            }
        }
        return userList.stream().distinct().collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing((c1)->c1.getUserName()))), ArrayList::new));
    }
}
