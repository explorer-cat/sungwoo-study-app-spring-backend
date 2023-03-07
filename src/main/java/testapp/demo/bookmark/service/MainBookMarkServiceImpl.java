package testapp.demo.bookmark.service;

import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testapp.demo.bookmark.dto.UserMainBookMarkDto;
import testapp.demo.bookmark.entity.MainCategoryBookMark;
import testapp.demo.bookmark.repository.MainCategoryBookMarkRepository;
import testapp.demo.category.entity.MainCategory;
import testapp.demo.category.repository.MainCategoryRepository;
import testapp.demo.member.dto.UserInfoResponseDto;
import testapp.demo.member.entity.Member;
import testapp.demo.member.repository.MemberRepository;
import testapp.demo.member.service.MemberServiceImpl;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MainBookMarkServiceImpl implements MainBookMarkService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MainCategoryRepository mainCategoryRepository;
    @Autowired
    private MainCategoryBookMarkRepository mainCategoryBookMarkRepository;


    @Override
    @Transactional
    public void addBookMark(String userEmail, Long mainCategoryId) {
        Member member = memberRepository.findByEmail(userEmail);

        MainCategory mainCategory = mainCategoryRepository.findById(mainCategoryId).get();
        MainCategoryBookMark mainCategoryBookMark = MainCategoryBookMark.createMainBookMark(member, mainCategory);

        mainCategoryBookMarkRepository.save(mainCategoryBookMark);
    }

    @Override
    @Transactional
    public void removeBookMark(String userEmail, Long mainCategoryId) {
        Member member = memberRepository.findByEmail(userEmail);
        MainCategory mainCategory = mainCategoryRepository.findById(mainCategoryId).get();

        Optional<MainCategoryBookMark> mainCategoryBookMark = mainCategoryBookMarkRepository.findByMemberAndMainCategory(member,mainCategory);

        if(mainCategoryBookMark.isPresent()) {
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
}
