//package letsit_backend.service;
//
//import letsit_backend.model.Member;
//import letsit_backend.repository.MemberRepository;
//import lombok.Data;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.util.ObjectUtils;
//import org.springframework.util.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//@Getter @Setter
//public class ImgService {
//
//    private final MemberRepository memberRepository;
//
//    public ResponseEntity<String> updateUserImg(Long kakaoId, MultipartFile imgFile) {
//        Optional<Member> memberOptional = memberRepository.findBykakaoId(kakaoId);
//
//        if (memberOptional.isPresent()) {
//            Member memberEntity = memberOptional.get();
//
//            try {
//                Member savedMemberEntity = fileHandler(imgFile, kakaoId);
//                if (savedMemberEntity != null) {
//                    if (memberEntity.getProfileImageUrl() != null) {
//                        deleteUserImg(kakaoId);
//                    }
//                    memberEntity.setProfileImageUrl(savedMemberEntity.getProfileImageUrl());
//                    memberRepository.save(memberEntity);
//
//                    return ResponseEntity.ok().body("이미지가 업데이트 되었습니다.");
//                } else {
//                    String errorMessage = "이미지를 업로드하는 중 오류가 발생했습니다.";
//                    System.err.println(errorMessage);
//                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
//                }
//            } catch (Exception e) {
//                String errorMessage = "이미지 업데이트 중 오류가 발생했습니다: " + e.getMessage();
//                System.err.println(errorMessage);
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
//            }
//        } else {
//            String errorMessage = "해당 이메일을 가진 사용자를 찾을 수 없습니다: " + kakaoId;
//            System.err.println(errorMessage);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
//        }
//    }
//
//    private Member fileHandler(MultipartFile file, String kakaoId) throws Exception {
//        String absolutePath = new File("").getAbsolutePath() + File.separator;
//        String path = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static"
//                + File.separator + "images" + File.separator + "userImg";
//        File userImg = new File(path);
//
//        if (!userImg.exists()) {
//            userImg.mkdirs();
//        }
//
//        if (!file.isEmpty()) {
//            String contentType = file.getContentType();
//            String originalFileExtension;
//
//            if (ObjectUtils.isEmpty(contentType)) {
//                return null;
//            } else {
//                if (contentType.contains("image/jpeg")) {
//                    originalFileExtension = ".jpg";
//                } else if (contentType.contains("image/png")) {
//                    originalFileExtension = ".png";
//                } else {
//                    return null;
//                }
//            }
//
//            String originalFileName = file.getOriginalFilename();
//            int lastIndex = originalFileName.lastIndexOf('.');
//            String fileName = originalFileName.substring(0, lastIndex);
//
//            String userImgName = fileName + System.nanoTime() + originalFileExtension;
//            userImg = new File(absolutePath + path + File.separator + userImgName);
//            System.out.println("파일 저장경로:" + absolutePath + path + File.separator + userImgName);
//            file.transferTo(userImg);
//
//            Member memberEntity = new Member();
//            memberEntity.setProfileImageUrl(path + File.separator + userImgName);
//
//            return memberEntity;
//        }
//
//        return null;
//    }
//
//    public ResponseEntity<String> deleteUserImg(String kakaoId) {
//        Optional<Member> memberOptional = memberRepository.findBykakaoId(kakaoId);
//
//        if (memberOptional.isPresent()) {
//            Member memberEntity = memberOptional.get();
//            try {
//                String imgPath = memberEntity.getProfileImageUrl();
//                File imgFile = new File(imgPath);
//
//                if (imgFile.exists()) {
//                    System.out.println("파일 존재함");
//                    if (imgFile.delete()) {
//                        memberEntity.setProfileImageUrl(null);
//                        memberRepository.save(memberEntity);
//
//                        return ResponseEntity.ok("이미지 삭제 성공");
//                    } else {
//                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 파일 삭제 실패");
//                    }
//                } else {
//                    return ResponseEntity.ok("이미지 파일이 이미 존재하지 않습니다.");
//                }
//            } catch (Exception e) {
//                System.err.println(e.getMessage());
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 삭제 오류");
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(kakaoId + " 해당 이메일을 가진 사용자를 찾을 수 없습니다");
//        }
//    }
//
//    public byte[] getUserProfileImage(Long kakaoId) throws IOException {
//        System.out.println("getUserProfileImage 실행");
//
//        Optional<Member> memberOptional = memberRepository.findByKakaoId(kakaoId);
//        if (memberOptional.isPresent()) {
//            Member memberEntity = memberOptional.get();
//            String profileImagePath = memberEntity.getProfileImageUrl();
//
//            System.out.println("profileImagePath: " + profileImagePath);
//
//            if (!StringUtils.isEmpty(profileImagePath)) {
//                Path imagePath = Paths.get(profileImagePath);
//                System.out.println("imagePath: " + imagePath);
//
//                if (Files.exists(imagePath)) {
//                    return Files.readAllBytes(imagePath);
//                } else {
//                    System.out.println("파일이 존재하지 않습니다.");
//                }
//            } else {
//                System.out.println("프로필 이미지 경로가 비어 있습니다.");
//            }
//        } else {
//            System.out.println("해당 유저가 존재하지 않습니다.");
//        }
//
//        return new byte[0];
//    }
//}
