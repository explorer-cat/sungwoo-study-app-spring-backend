package testapp.demo.category.service;

import org.springframework.stereotype.Service;
import testapp.demo.category.dto.mainCategory.CreateMainCategoryRequest;
import testapp.demo.category.dto.mainCategory.MainCategoryResponseDTO;
import testapp.demo.category.entity.MainCategory;
import testapp.demo.category.repository.MainCategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MainCategoryServiceImpl implements MainCategoryService {
    private final MainCategoryRepository mainCategoryRepository;

    public MainCategoryServiceImpl(MainCategoryRepository mainCategoryRepository) {
        this.mainCategoryRepository = mainCategoryRepository;
    }


    /**
     * @title 카테고리 단건 생성합니다.
     * @param request CreateMainCategoryRequest 카테고리 생성을 위한 DTO
     * @return MainCategoryResponseDTO
     * @Created 2023.02
     */
    @Override
    public MainCategoryResponseDTO createCategory(CreateMainCategoryRequest request) {
            //CreateMainCategoryRequest DTO를 엔티티객체로 변환
            MainCategory mainCategory = request.toEntity();
            MainCategoryResponseDTO response = new MainCategoryResponseDTO();

            //동일한 메인 카테고리 이름이 존재하는지 확인
            Optional<MainCategory> existMainCategory = mainCategoryRepository.findByName(request.getName());

            //이미 동일한 카테고리 이름이 존재 할 경우
            if (existMainCategory.isPresent()) {
                //예외 발생
                throw new IllegalStateException("exist category: "+request.getName());
            } else {
                //존재하지 않는 경우 데이터베이스 저장 시작
                MainCategory save = mainCategoryRepository.save(mainCategory);
                return response.fromEntity(save);
            }
    }


    /**
     * @title 전체 메인 카테고리 정보를 찾습니다.
     * @return
     * @created 2023.02
     */
    @Override
    public List<MainCategoryResponseDTO> findAllCategory() {
        List<MainCategory> categories = mainCategoryRepository.findAll();
        List<MainCategoryResponseDTO> result = new ArrayList<>();

        //DB에서 받은 값들을 DTO에 맵핑 시켜주는 작업
        //DB에서 받은 값들이 없다면 반복문을 돌지않고 빈 리스트만 리턴.
        for (MainCategory v : categories) {
            //Find 한 리스트 안에 있는 MainCategory 객체를 DTO로 맵핑 시켜 하나씩 리스트 안에 넣어줌
            MainCategoryResponseDTO dto = new MainCategoryResponseDTO();
            result.add(dto.fromEntity(v));
        }

        return result;
    }

    @Override
    public MainCategoryResponseDTO findCategory(long categoryId) {
        Optional<MainCategory> category = mainCategoryRepository.findById(categoryId);
        MainCategoryResponseDTO dto = new MainCategoryResponseDTO();

        //카테고리가 존재할 경우
        if (category.isPresent()) {
            return dto.fromEntity(category.get());
        } else {
            throw new NullPointerException("Null MainCategory");
        }
    }

    @Override
    public void removeCategory(long mainCategoryId) throws Exception {
        try{
            mainCategoryRepository.deleteById(mainCategoryId);
        } catch (Exception ex) {
            throw new Exception("error");
        }
    }
}
