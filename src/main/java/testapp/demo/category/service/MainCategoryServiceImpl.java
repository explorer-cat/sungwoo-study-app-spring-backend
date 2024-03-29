package testapp.demo.category.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import testapp.demo.category.dto.mainCategory.CreateMainCategoryRequest;
import testapp.demo.category.dto.mainCategory.MainCategoryResponseDTO;
import testapp.demo.category.entity.MainCategory;
import testapp.demo.bookmark.entity.MainCategoryBookMark;
import testapp.demo.category.entity.SubCategory;
import testapp.demo.bookmark.repository.MainCategoryBookMarkRepository;
import testapp.demo.category.repository.MainCategoryRepository;
import testapp.demo.category.repository.SubCategoryRepository;
import testapp.demo.member.entity.Member;
import testapp.demo.member.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class MainCategoryServiceImpl implements MainCategoryService {
    @Autowired private MainCategoryRepository mainCategoryRepository;
    @Autowired private SubCategoryRepository subCategoryRepository;
    /**
     * @param request CreateMainCategoryRequest 카테고리 생성을 위한 DTO
     * @return MainCategoryResponseDTO
     * @title 카테고리 단건 생성합니다.
     * @Created 2023.02
     */
    @Override
    public MainCategoryResponseDTO createCategory(CreateMainCategoryRequest request) {

        Optional<MainCategory> byId = mainCategoryRepository.findById(request.getId());
        if(byId.isPresent()) {
            throw new IllegalStateException("exist category: " + request.getName());
        }

        //CreateMainCategoryRequest DTO를 엔티티객체로 변환
        MainCategory mainCategory = request.toEntity();
        //동일한 메인 카테고리 이름이 존재하는지 확인
        Optional<MainCategory> existMainCategory = mainCategoryRepository.findByName(request.getName());

        //이미 동일한 카테고리 이름이 존재 할 경우
        if (existMainCategory.isPresent()) {
            //예외 발생
            throw new IllegalStateException("exist category: " + request.getName());
        } else {
            //존재하지 않는 경우 데이터베이스 저장 시작
            MainCategory save = mainCategoryRepository.save(mainCategory);
//                return response.fromEntity(save);
            return null;
        }
    }

    @Override
    public void modifyCategory(long mainCategoryId, CreateMainCategoryRequest request) {
        Optional<MainCategory> findCategory = mainCategoryRepository.findById(mainCategoryId);

        Optional<MainCategory> byName = mainCategoryRepository.findByName(request.getName());

        if(byName.isPresent()) {
            throw new IllegalStateException();
        }

        if(findCategory.isPresent()) {
            findCategory.get().setName(request.getName());
            findCategory.get().setDescription(request.getDescription());

            mainCategoryRepository.save(findCategory.get());
        }
    }


    /**
     * @return
     * @title 전체 메인 카테고리 정보를 찾습니다.
     * @created 2023.02
     */
    @Override
    public List<MainCategoryResponseDTO> findAllCategory() {
        List<MainCategory> categories = mainCategoryRepository.findAll();
        List<MainCategoryResponseDTO> result = new ArrayList<>();

        //DB에서 받은 값들을 DTO에 맵핑 시켜주는 작업
        //DB에서 받은 값들이 없다면 반복문을 돌지않고 빈 리스트만 리턴.
        for (MainCategory v : categories) {
            MainCategoryResponseDTO dto = new MainCategoryResponseDTO();
            result.add(dto.fromEntity(v, getSubCategories(v.getId())));
        }
        return result;
    }

    @Override
    public MainCategoryResponseDTO findCategory(long categoryId) {
        Optional<MainCategory> category = mainCategoryRepository.findById(categoryId);
        MainCategoryResponseDTO dto = new MainCategoryResponseDTO();

        //카테고리가 존재할 경우
        if (category.isPresent()) {
            return dto.fromEntity(category.get(), getSubCategories(categoryId));
        } else {
            throw new NullPointerException("Null MainCategory");
        }
    }

    /**
     * @param mainCategoryId
     * @return
     * @title 메인 카테고리 ID로 해당 서브카테고리 정보를 반환하는 메서드
     */
    @Override
    public List<Map<String, Object>> getSubCategories(long mainCategoryId) {
        List<Map<String, Object>> subCategories = new ArrayList<>();
        List<SubCategory> byMainCategoryId = subCategoryRepository.findByMainCategoryId(mainCategoryId);

        for (SubCategory subCategory : byMainCategoryId) {
            Map<String, Object> info = new HashMap<>();
            info.put("id", subCategory.getId());
            info.put("name", subCategory.getName());
            info.put("description", subCategory.getDescription());
            subCategories.add(info);
        }
        return subCategories;
    }



    @Override
    public void removeCategory(long mainCategoryId) throws Exception {
        try {
            mainCategoryRepository.deleteById(mainCategoryId);
        } catch (Exception ex) {
            throw new Exception("error");
        }
    }


//
//    @Override
//    public ResponseEntity addBookMark(String userEmail, long mainCategory) {
//        //컨트롤러에서 넘어온 사용자 이메일로 멤버 엔티티 객체 찾기.
//        Member member = memberRepository.findByEmail(userEmail);
//        //컨트롤러에서 넘어온 메인 카테고리 아이디로 메인카테고리 엔티티 객체 찾기.
//        MainCategory category = mainCategoryRepository.findById(mainCategory).get();
//
//        //북마크 저장
//        MainCategoryBookMark mainCategoryBookMark = new MainCategoryBookMark();
//        mainCategoryBookMark.setMember(member);
//        mainCategoryBookMark.setMainCategory(category);
//        mainCategoryBookMark.setCreateDate(LocalDateTime.now());
//
//        bookMarkRepository.save(mainCategoryBookMark);
//        return null;
//    }


}
