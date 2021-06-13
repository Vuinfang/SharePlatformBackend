package cn.hogrider.shareplatform.controller;

import cn.hogrider.shareplatform.entity.Application;
import cn.hogrider.shareplatform.repository.ApplicationRepository;
import cn.hogrider.shareplatform.repository.OngoingApplicationRepository;
import cn.hogrider.shareplatform.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ongoingApplication")
public class OngoingApplicationHandler {
    @Autowired
    private OngoingApplicationRepository ongoingApplicationRepository;

    //=====================
    //for personal applied
    //unstarted job
    //to get applied job
    @GetMapping("/ongoingJob/{page}/{size}/{username}")
    public List ongoingJob(@PathVariable("page") Integer page
            , @PathVariable("size") Integer size, @PathVariable("username") String username){
        Pageable pageable = PageRequest.of(page-1,size);
        return ongoingApplicationRepository.findAppsByTaskID(username,pageable);
    }

    //find total applied job number
    @GetMapping("/totalOngoingJob/{username}")
    public Integer totalOngoingJob(@PathVariable("username") String username){
        return ongoingApplicationRepository.totalApplication(username);
    }
    //get all task posted related to keyword
    @GetMapping("/ongoingJobSearch/{page}/{size}/{username}/{keyword}")
    public List OngoingJobSearch(@PathVariable("page") Integer page
            , @PathVariable("size") Integer size, @PathVariable("username") String username, @PathVariable("keyword") String keyword){
        Pageable pageable = PageRequest.of(page-1,size);
        keyword = "%"+keyword+"%";
        return ongoingApplicationRepository.findAppByKeyword(username,keyword,pageable);
    }
    //find total task number by search keyword
    @GetMapping("/totalOngoingJobSearch/{username}/{keyword}")
    public Integer totalOngoingJobSearch(@PathVariable("username") String username, @PathVariable("keyword") String keyword){
        keyword = "%"+keyword+"%";
        return ongoingApplicationRepository.unstartedTotal(username,keyword);
    }
    //=================================

}
