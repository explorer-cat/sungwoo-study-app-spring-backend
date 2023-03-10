package testapp.demo.bookmark.service;

import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testapp.demo.bookmark.dto.UserMainBookMarkDto;
import testapp.demo.bookmark.dto.UserSubBookMarkDetailDto;
import testapp.demo.bookmark.dto.UserSubBookMarkDto;
import testapp.demo.bookmark.entity.MainCategoryBookMark;
import testapp.demo.bookmark.entity.SubCategoryBookMark;
import testapp.demo.bookmark.mapping.BookMarkIdMapping;
import testapp.demo.bookmark.repository.MainCategoryBookMarkRepository;
import testapp.demo.bookmark.repository.SubCategoryBookMarkRepository;
import testapp.demo.category.dto.subCategory.SubCategoryResponseDTO;
import testapp.demo.category.entity.MainCategory;
import testapp.demo.category.entity.SubCategory;
import testapp.demo.category.repository.MainCategoryRepository;
import testapp.demo.category.repository.SubCategoryRepository;
import testapp.demo.member.entity.Member;
import testapp.demo.member.repository.MemberRepository;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class BookMarkServiceImpl implements BookMarkService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MainCategoryRepository mainCategoryRepository;
    @Autowired
    private MainCategoryBookMarkRepository mainCategoryBookMarkRepository;
    @Autowired
    private SubCategoryBookMarkRepository subCategoryBookMarkRepository;
    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Override
    @Transactional
    public void addBookMark(String userEmail, long mainCategoryId) {
        Member member = memberRepository.findByEmail(userEmail);
        MainCategory mainCategory = mainCategoryRepository.findById(mainCategoryId).get();

        Optional<MainCategoryBookMark> has_bookmark = mainCategoryBookMarkRepository.findByMemberAndMainCategory(member, mainCategory);

        //데이터베이스에 없는 경우에만 새로 추가함.
        if (!has_bookmark.isPresent()) {
            MainCategoryBookMark mainCategoryBookMark = MainCategoryBookMark.createMainBookMark(member, mainCategory);
            mainCategoryBookMarkRepository.save(mainCategoryBookMark);
        }
    }

    @Override
    @Transactional
    public void removeBookMark(String userEmail, long mainCategoryId) {
        Member member = memberRepository.findByEmail(userEmail);
        MainCategory mainCategory = mainCategoryRepository.findById(mainCategoryId).get();

        Optional<MainCategoryBookMark> mainCategoryBookMark = mainCategoryBookMarkRepository.findByMemberAndMainCategory(member, mainCategory);

        if (mainCategoryBookMark.isPresent()) {
            mainCategoryBookMarkRepository.deleteByMemberAndMainCategory(member, mainCategory);
        } else {
            //해당 사용자가 북마크한 정보를 찾을 수 없음.
            throw new NoSuchElementException();
        }
    }

    @Override
    public List<MainCategoryBookMark> getMainBookMark(String userEmail) throws NotFound {
        //사용자가 이메일로 조회할 때
        Member member = memberRepository.findByEmail(userEmail);
        return member.getMainCategoryBookMark();
    }

    @Override
    @Transactional
    public void addSubCategoryBookMark(String userEmail, long subCategoryId) {
        Member member = memberRepository.findByEmail(userEmail);
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId).get();

        Optional<SubCategoryBookMark> has_bookmark = subCategoryBookMarkRepository.findByMemberAndSubCategory(member, subCategory);

        //데이터베이스에 없는 경우에만 새로 추가함.
        if (!has_bookmark.isPresent()) {
            SubCategoryBookMark subCategoryBookMark = SubCategoryBookMark.createSubCategory(member, subCategory,subCategory.getMainCategory());
            subCategoryBookMarkRepository.save(subCategoryBookMark);
        }
    }

    @Override
    @Transactional
    public void removeSubBookMark(String userEmail, long subCategoryId) throws NotFound {
        Member member = memberRepository.findByEmail(userEmail);
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId).get();

        Optional<SubCategoryBookMark> subCategoryBookMark = subCategoryBookMarkRepository.findByMemberAndSubCategory(member, subCategory);

        if (subCategoryBookMark.isPresent()) {
            subCategoryBookMarkRepository.deleteByMemberAndSubCategory(member, subCategory);
        } else {
            //해당 사용자가 북마크한 정보를 찾을 수 없음.
            throw new NoSuchElementException();
        }
    }

    @Override
    public List<UserSubBookMarkDto> getAllSubBookMark(String userEmail) {
        //요청한 사용자의 정보를 가져옴.
        Member member = memberRepository.findByEmail(userEmail);
        List<MainCategory> all = mainCategoryRepository.findAll();
        List<UserSubBookMarkDto> dto = new ArrayList<>();

        //북마크하고있는 서브카테고리를 사용자를 디비에서 찾음. BookMarkIdMapping ID값들만 맵핑해서 가져옴.
        List<BookMarkIdMapping> byMainCategoryAndMember_key_list = subCategoryBookMarkRepository.findAllByMember(member);

        //위에서 가져온값들을 Id만 따로 가져와서 Set에 저장
        Set<Long> user_book_mark_list_key = new HashSet<>();

        for (BookMarkIdMapping bookMarkIdMapping : byMainCategoryAndMember_key_list) {
            user_book_mark_list_key.add(bookMarkIdMapping.getId());
        }

        for (MainCategory mainCategory : all) {
            //한 메인카테고리에 넣고 초기화
            List<UserSubBookMarkDetailDto> detail_sub_category = new ArrayList<>();

            //해당 메인카테고리의 서브카테고리 조회
            List<SubCategory> subCategories = mainCategory.getSubCategories();
            for (SubCategory subCategory : subCategories) {

                detail_sub_category.add(UserSubBookMarkDetailDto.builder()
                        .sub_category_id(subCategory.getId())
                        .sub_category_name(subCategory.getName())
                        .selected(user_book_mark_list_key.contains(subCategory.getId()))//전체 다 선택되어있는 상태
                        .createdDate(subCategory.getCreateDate())
                        .build());
            }
            //반환 DTO 세팅
            UserSubBookMarkDto build = UserSubBookMarkDto.builder()
                    .main_category_id(mainCategory.getId())
                    .main_category_name(mainCategory.getName())
                    .bookmark_sub_categories(detail_sub_category)
                    .build();

            dto.add(build);
        }
        return dto;
    }

    @Override
    public List<UserSubBookMarkDto> getSelctedSubBookMark(String userEmail) {
        //요청한 사용자의 정보를 가져옴.
        Member member = memberRepository.findByEmail(userEmail);

        List<SubCategoryBookMark> subCategoryBookMark = member.getSubCategoryBookMark();
        //사용자가 북마크 하고있는 서브카테고리의 부모 메인카테고리를 중복없이 반환하기 위한 Set
        Set<MainCategory> user_book_mark_list = new HashSet<>();

        //사용자가 북마크한 서브 카테고리 정보들 중에서 메인 카테고리 정보들만 중복 가져와서 중복제거함
        for (SubCategoryBookMark categoryBookMark : subCategoryBookMark) {
            user_book_mark_list.add(categoryBookMark.getMainCategory());
        }

        //user_book_mark_list  <- 현재 중복 제거된 메인카테고리 정보가 담겨있음.
        Set<MainCategory> userBookMarkList = user_book_mark_list;

        List<UserSubBookMarkDto> dto = new ArrayList<>();

        //DTO 반환을 위한 사용자가 북마크 하고있는 메인 카테고리 개수만큼  반복 시작
        for (MainCategory mainCategory : userBookMarkList) {

            //해당 메인 카테고리를 북마크 하고있는 And 사용자를 디비에서 찾음.
            List<SubCategoryBookMark> byMainCategoryAndMember = subCategoryBookMarkRepository.findByMainCategoryAndMember(mainCategory, member);

            List<UserSubBookMarkDetailDto> detail_sub_category = new ArrayList<>();

            //byMainCategoryAndMember 개수 = subCategorybook마크의 개수임. 해당 정보들은 dto로 한번더 만들어서 반환 dto 에 넣어줌.
            for (SubCategoryBookMark categoryBookMark : byMainCategoryAndMember) {
                detail_sub_category.add(UserSubBookMarkDetailDto.builder()
                        .sub_category_id(categoryBookMark.getSubCategory().getId())
                        .sub_category_name(categoryBookMark.getSubCategory().getName())
                        .selected(true)//전체 다 선택되어있는 상태
                        .createdDate(categoryBookMark.getCreateDate())
                        .build());
            }

            //반환 DTO 세팅
            UserSubBookMarkDto build = UserSubBookMarkDto.builder()
                    .main_category_id(mainCategory.getId())
                    .main_category_name(mainCategory.getName())
                    .bookmark_sub_categories(detail_sub_category)
                    .build();

            dto.add(build);
        }

        return dto;
    }
}
