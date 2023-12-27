package com.lec.spring.withbuddy_project.service;

import com.lec.spring.withbuddy_project.domain.Authority;
import com.lec.spring.withbuddy_project.domain.MypagePet;
import com.lec.spring.withbuddy_project.domain.User;
import com.lec.spring.withbuddy_project.domain.dto.BuddyDto;
import com.lec.spring.withbuddy_project.domain.dto.UserDto;
import com.lec.spring.withbuddy_project.repository.AuthorityRepository;
import com.lec.spring.withbuddy_project.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    //파일 업로드 경로
    @Value("${app.upload.path}")
    private String uploadDir;


    @Autowired
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;



    public UserServiceImpl(SqlSession sqlSession) {
        userRepository = sqlSession.getMapper(UserRepository.class);
        authorityRepository = sqlSession.getMapper(AuthorityRepository.class);
    }


    public User findByUsername(String userId) {
        return userRepository.findByUsername(userId);
    }

    @Override
    public List<UserDto> findWithoutMe(Long id,Long addressId) { // 나 뺴고 찾음
        return userRepository.findAllWithoutId(id,addressId);
    }


    @Override
    public User findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public BuddyDto findBuddyProfile(Long id, Long code) {
        return userRepository.findUserById(id,code);
    }

    @Override
    public List<User> findDm(Long loginId) { // 로그인한 유저가 갖고있는 모든 dm채팅방 정보

        List<User> list = userRepository.findDmListByLoginUserId(loginId);
        log.info("dmList user: {} ", list);
        return list;
    }

    @Override
    public List<UserDto> findAllUser(Long id) {
        return userRepository.findAllUser(id);
    }

    @Override
    public List<Authority> selectAuthoritiesById(Long id) {
        User user = userRepository.findById(id);
        return authorityRepository.findByUser(user);
    }

    @Override
    public boolean isExist(String userId) {
        User user = findByUsername(userId);
        return (user != null) ? true : false;
    }

    @Override
    public boolean isExistEmail(String email) {
        User user = userRepository.findByEmail(email);
        return (user != null) ? true : false;
    }


    @Override
    public int register(User user) {
        // DB 에는 회원 username 을 대문자로 저장
        user.setUserId(user.getUserId().toUpperCase());

        // password 는 암호화 해서 저장.  PasswordEncoder 객체 사용
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);  // 새로운 회원 (User) 저장.  id 값 받아옴.

        // 신규회원은 ROLE_MEMBER 권한을 부여하기
//        Authority auth = authorityRepository.findByName("ROLE_MEMBER");


//        int auth_id = auth.getId();
//        String authorityName = auth.getAuthorityName();
//        authorityRepository.addAuthority(auth_id,authorityName);

        return 1;
    }

    @Override
    public void buddyregister(MypagePet mypagePet, Map<String, MultipartFile> file) {

        addFile(file, mypagePet);

//        userRepository.buddy(mypagePet);
    }

    // 특정 글(id) 첨부파일(들) 추가
    private void addFile(Map<String, MultipartFile> files, MypagePet mypagePet) {
        if (files != null) {
            for (var e : files.entrySet()) {

                // name="upfile##" 인 경우만 첨부파일 등록. (이유, 다른 웹에디터와 섞이지 않도록..ex: summernote)
                if (!e.getKey().startsWith("buddyFile")) continue;

                // 첨부 파일 정보 출력
                System.out.println("\n첨부파일 정보: " + e.getKey());   // name값
//                U.printFileInfo(e.getValue());   // 파일 정보 출력
                System.out.println();

                // 물리적인 파일 저장
                mypagePet.setBuddyImage(upload(e.getValue()));

                // 성공하면 DB 에도 저장
                if (mypagePet.getBuddyImage() != null) {
//                    file.setBuddyId(id);   // FK 설정
                    userRepository.buddy(mypagePet);   // INSERT
                }
            }
        }
    } // end addFiles()

    private String upload(MultipartFile multipartFile) {
        MypagePet mypagePet = null;

        // 담긴 파일이 없으면 pass
        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) return null;

        // 원본파일명
        // 저장될 파일명
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        // 파일명 이 중복되는지 확인
        File file = new File(uploadDir, fileName);

        // a.txt => a_2378142783946.txt  : time stamp 값을 활용할거다!
        int pos = fileName.lastIndexOf(".");
        if (pos > -1) {   // 확장자가 있는 경우
            String name = fileName.substring(0, pos);  // 파일 '이름'
            String ext = fileName.substring(pos + 1);   // 파일 '확장자'

            // 중복방지를 위한 새로운 이름 (현재시간 ms) 를 파일명에 추가
            fileName = name + "_" + System.currentTimeMillis() + "." + ext;
        } else {  // 확장자가 없는 경우
            fileName += "_" + System.currentTimeMillis();
        }
        // 저장할 파일명
        System.out.println("fileName: " + fileName);

        // java.nio
        Path copyOfLocation = Paths.get(new File(uploadDir, fileName).getAbsolutePath());
        System.out.println(copyOfLocation);

        try {
            // inputStream을 가져와서
            // copyOfLocation (저장위치)로 파일을 쓴다.
            // copy의 옵션은 기존에 존재하면 REPLACE(대체한다), 오버라이딩 한다

            Files.copy(
                    multipartFile.getInputStream(),
                    copyOfLocation,
                    StandardCopyOption.REPLACE_EXISTING    // 기존에 존재하면 덮어쓰기
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        attachment = Attachment.builder()
//                .filename(fileName)   // 저장된 이름
//                .sourcename(sourceName)  // 원본 이름
//                .build();

        return fileName;
    }

    // 아이디 찾기 12/24
    @Override
    public User findUsernameByEmail(String email) {
        System.out.println("Searching for user by email: " + email);
        return userRepository.findByEmail(email);
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        if (user != null) {
            // 새로운 비밀번호를 인코딩하여 저장
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.update(user);
        }
    }



    @Override
    public String generateNewPassword() {
        String randomCode = UUID.randomUUID().toString().substring(0,5);
        return randomCode;
    }

    // 주소 넣기
    @Override
    public void address(User user) {
        userRepository.update(user);
    }

    @Override
    public boolean findBuddy(Long id) {
        MypagePet mypagePet = userRepository.findBuddy(id);
        return (mypagePet == null) ? true : false;
    }

    @Override
    public String getAuthorityName(Long id) {

        return userRepository.findByAuthorityName(id);
    }


}
