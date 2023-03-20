package testapp.demo.category.service;

import org.springframework.stereotype.Service;
import testapp.demo.category.dto.mainCategory.CreateMainCategoryRequest;
import testapp.demo.category.dto.mainCategory.MainCategoryResponseDTO;
import testapp.demo.category.dto.subCategory.CreateSubCategoryRequest;
import testapp.demo.category.dto.subCategory.SubCategoryResponseDTO;
import testapp.demo.category.entity.MainCategory;
import testapp.demo.category.entity.SubCategory;
import testapp.demo.category.repository.MainCategoryRepository;
import testapp.demo.category.repository.SubCategoryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final MainCategoryRepository mainCategoryRepository;

    public SubCategoryServiceImpl(SubCategoryRepository subCategoryRepository, MainCategoryRepository mainCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
        this.mainCategoryRepository = mainCategoryRepository;
    }

    @Override
    public void createCategory(long mainCategoryId, CreateSubCategoryRequest request) {
        //서브카테고리를 생성할 메인카테고리가 존재 하는지 확인
        Optional<MainCategory> existMainCategory = mainCategoryRepository.findById(mainCategoryId);

        if(existMainCategory.isPresent()) {
            //생성 request dto에 해당 메인카테고리 아이디 정보 세팅
            request.setMainCategory(existMainCategory.get());
        } else {
            //존재하지 않는 메인카테고리에 서브카테고리 생성을 시도함.
            throw new IllegalStateException("notFound_Category");
        }

        //메게변수로 받은 생성 DTO를 Entity객체로 변환함.
        SubCategory subCategory = request.toEntity();
        //동일한 메인 카테고리 이름이 존재하는지 확인
        Optional<SubCategory> existSubCategory = subCategoryRepository.findByName(request.getName());
        //이미 동일한 카테고리 이름이 존재 할 경우
        if (existSubCategory.isPresent()) {
            //예외 발생
            throw new IllegalStateException("exist");
        } else {
            //존재하지 않는 경우 데이터베이스 저장 시작
            SubCategory save = subCategoryRepository.save(subCategory);
        }
    }

    @Override
    public SubCategoryResponseDTO removeCategory(long mainCategoryId) {
        return null;
    }

    @Override
    public List<SubCategoryResponseDTO> getAllSubCategory(long mainCategoryId) {
        List<SubCategoryResponseDTO> result = new ArrayList<>();
        //1번 서브카테고리 정보를 요청
        List<SubCategory> categories = subCategoryRepository.findByMainCategoryId(mainCategoryId);

        System.out.println("categories = " + categories);
        for (SubCategory v : categories) {
            System.out.println("v = " + v);
            SubCategoryResponseDTO dto = new SubCategoryResponseDTO();
            result.add(dto.fromEntity(v));
        }
        if (result.isEmpty()) {
            throw new IllegalStateException("Empty");
        } else {
            return result;
        }
    }
}
