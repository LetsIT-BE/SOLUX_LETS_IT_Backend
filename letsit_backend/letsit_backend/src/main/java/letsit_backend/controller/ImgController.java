//package letsit_backend.controller;
//
//import letsit_backend.service.ImgService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;  // MultipartFile import 추가
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/profile")
//public class ImgController {
//
//    private final ImgService imgService;
//
//    @PostMapping("/upload")
//    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("kakaoId") String kakaoId) {
//
//        System.out.println("프로필 업데이트 컨트롤러 실행");
//
//        String fileName = file.getOriginalFilename();
//        String contentType = file.getContentType();
//
//        System.out.println("fileName: " + fileName);
//        System.out.println("contentType: " + contentType);
//
//        try {
//            imgService.updateUserImg(kakaoId, file);
//            return ResponseEntity.ok("User information updated successfully");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Failed to update user information: " + e.getMessage());
//        }
//    }
//
//    @GetMapping("/image")
//    public ResponseEntity<byte[]> getUserImg(@RequestParam String kakaoId) {
//
//        System.out.println("유저 사진 정보 컨트롤러 실행");
//
//        try {
//            byte[] imageBytes = imgService.getUserProfileImage(kakaoId);
//
//            System.out.println("imageBytes: " + imageBytes);
//            if (imageBytes != null && imageBytes.length > 0) {
//                HttpHeaders headers = new HttpHeaders();
//                headers.setContentType(MediaType.IMAGE_JPEG); // 필요에 따라 변경
//                return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//}
