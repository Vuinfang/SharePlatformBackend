package cn.hogrider.shareplatform.controller;

import cn.hogrider.shareplatform.entity.Application;
import cn.hogrider.shareplatform.repository.ApplicationRepository;
import cn.hogrider.shareplatform.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/application")
public class ApplicationHandler {
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/findAll")
    public List<Application> findAll(){
        return applicationRepository.findAll();
    }

    @GetMapping("/findOne/{id}/{applicant}")
    public String findAll(@PathVariable("id") Integer id, @PathVariable("applicant") String applicant){
        if(applicationRepository.findByTaskIDAndAndApplicant(id,applicant) != null){
            return "success";
        }else{
            return "exist";
        }
    }


    @PostMapping("/save")
    public String save(@RequestBody Application application){
        if(applicationRepository.findByTaskIDAndAndApplicant(application.getTaskID(),application.getApplicant()) != null){
            return "applied";
        }
        Application result = applicationRepository.save(application);
        if(result != null){
            return "success";
        } else {
            return "error";
        }
    }
    @GetMapping("/search/{keyword}")
    public List<Application> search(@PathVariable("keyword") String keyword){
        return applicationRepository.findByApplicant(keyword);
    }
    //=====================
    //for personal applied
    //unstarted job
    //to get applied job
    @GetMapping("/unstartedJob/{page}/{size}/{username}")
    public List unstartedJob(@PathVariable("page") Integer page
            , @PathVariable("size") Integer size, @PathVariable("username") String username){
        Pageable pageable = PageRequest.of(page-1,size);
        return applicationRepository.findAppsByTaskID(username,pageable);
    }

    //find total applied job number
    @GetMapping("/totalUnstartedJob/{username}")
    public Integer totalUnstartedJob(@PathVariable("username") String username){
        return applicationRepository.totalApplication(username);
    }
    //get all task posted related to keyword
    @GetMapping("/unstartedJobSearch/{page}/{size}/{username}/{keyword}")
    public List unstartedJobSearch(@PathVariable("page") Integer page
            , @PathVariable("size") Integer size, @PathVariable("username") String username, @PathVariable("keyword") String keyword){
        Pageable pageable = PageRequest.of(page-1,size);
        keyword = "%"+keyword+"%";
        return applicationRepository.findAppByKeyword(username,keyword,pageable);
    }
    //find total task number by search keyword
    @GetMapping("/totalUnstartedJobSearch/{username}/{keyword}")
    public Integer totalUnstartedJobSearch(@PathVariable("username") String username, @PathVariable("keyword") String keyword){
        keyword = "%"+keyword+"%";
        return applicationRepository.unstartedTotal(username,keyword);
    }
    //=================================

}
