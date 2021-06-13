package cn.hogrider.shareplatform.controller;

import cn.hogrider.shareplatform.entity.Application;
import cn.hogrider.shareplatform.entity.OngoingApplication;
import cn.hogrider.shareplatform.entity.OngoingTask;
import cn.hogrider.shareplatform.entity.Task;
import cn.hogrider.shareplatform.repository.ApplicationRepository;
import cn.hogrider.shareplatform.repository.OngoingApplicationRepository;
import cn.hogrider.shareplatform.repository.OngoingTaskRepository;
import cn.hogrider.shareplatform.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task")
public class TaskHandler {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private OngoingTaskRepository ongoingTaskRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private OngoingApplicationRepository ongoingApplicationRepository;

//    @GetMapping("/findAll")
//    public List<Task> findAll(){
//        return taskRepository.findAll();
//    }

    @GetMapping("/findAll/{page}/{size}")
    public Page<Task> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable = PageRequest.of(page-1,size);

        return taskRepository.findAll(pageable);
    }
    @GetMapping("/keyword/{page}/{size}/{keyword}")
    public Page<Task> keyword(@PathVariable("page") Integer page
            , @PathVariable("size") Integer size, @PathVariable("keyword") String keyword){
        Pageable pageable = PageRequest.of(page-1,size);
        keyword = "%"+keyword+"%";
        return taskRepository.searchKeyword(keyword,pageable);
    }

    @GetMapping("/showTotal")
    public Integer showTotal(){
        return taskRepository.showTotal();
    }

    @GetMapping("/totalLikeKeyword/{keyword}")
    public Integer totalLikeKeyword(@PathVariable("keyword") String keyword){
        keyword = "%"+keyword+"%";
        return taskRepository.totalLikeKeyword(keyword);
    }

    @GetMapping("/agree/{taskID}/{applicationID}")
    public String findById(@PathVariable("taskID") Integer taskId, @PathVariable("applicationID") Integer applicationID)
    {
        Optional<Task> task = taskRepository.findById(taskId);
        System.out.println(task);
        if(task.isPresent()){
            Task temTask = task.get();
            System.out.println(temTask);
            Optional<Application> application = applicationRepository.findById(applicationID);
            if(application.isPresent()){

                OngoingTask ongoingTask = new OngoingTask();

                ongoingTask.setDetails(temTask.getDetails());
                ongoingTask.setId(temTask.getId());
                ongoingTask.setPrice(temTask.getPrice());
                ongoingTask.setTag(temTask.getTag());
                ongoingTask.setTitle(temTask.getTitle());
                ongoingTask.setUsername(temTask.getUsername());
                ongoingTaskRepository.save(ongoingTask);
                taskRepository.delete(temTask);
                Application temApplication = application.get();
                System.out.println(temApplication);
                OngoingApplication ongoingApplication = new OngoingApplication();
                ongoingApplication.setApplicant(temApplication.getApplicant());
                ongoingApplication.setDetails(temApplication.getDetails());
                ongoingApplication.setId(temApplication.getId());
                ongoingApplication.setTaskID(temApplication.getTaskID());
                ongoingApplicationRepository.save(ongoingApplication);
                applicationRepository.deleteByTaskID(temApplication.getTaskID());
//                applicationRepository.delete(temApplication);
                return "success";
            }
            return "success";
        }
        return "error";

    }

    @PostMapping("/save")
    public String save(@RequestBody Task task){
        Task result = taskRepository.save(task);
        if(result != null){
            return "success";
        } else {
            return "error";
        }
    }
    @GetMapping("/search/{keyword}")
    public List<Task> search(@PathVariable("keyword") String keyword){
        String word = "%"+keyword+"%";
        return taskRepository.findByTitleLike(word);
    }
//    @GetMapping("/applied/{keyword}")
//    public List<Application> applied(@PathVariable("keyword") String keyword){
//        List<Application> list = applicationRepository.findByApplicant(keyword);
//        return list;
//    }

//    @GetMapping("/posted/{keyword}")
//    public List<Task> postedTask(@PathVariable("keyword") String keyword){
//        return taskRepository.findByUsername(keyword);
//    }
    //=================================for personal - unstarted page
    @GetMapping("/posted/{keyword}")
    public List postedTask(@PathVariable("keyword") String keyword){
        return taskRepository.findByUser(keyword);
    }
    //get all task posted
    @GetMapping("/posted/{page}/{size}/{username}")
    public List postedTask(@PathVariable("page") Integer page
            , @PathVariable("size") Integer size, @PathVariable("username") String username){
        Pageable pageable = PageRequest.of(page-1,size);
        return taskRepository.findByUser(username,pageable);
    }
    //find total task number
    @GetMapping("/unstartedTotal/{username}")
    public Integer unstartedTotal(@PathVariable("username") String username){
        return taskRepository.unstartedTotal(username);
    }
    //get all task posted related to keyword
    @GetMapping("/postedSearch/{page}/{size}/{username}/{keyword}")
    public List postedTask(@PathVariable("page") Integer page
            , @PathVariable("size") Integer size, @PathVariable("username") String username, @PathVariable("keyword") String keyword){
        Pageable pageable = PageRequest.of(page-1,size);
        keyword = "%"+keyword+"%";
        return taskRepository.findByKeyword(username,keyword,pageable);
    }
    //find total task number by search keyword
    @GetMapping("/unstartedSearchTotal/{username}/{keyword}")
    public Integer unstartedTotal(@PathVariable("username") String username, @PathVariable("keyword") String keyword){
        keyword = "%"+keyword+"%";
        return taskRepository.unstartedTotal(username,keyword);
    }

    //=============================================

    @GetMapping("/showApplicants/{id}")
    public List showApplicants(@PathVariable("id") Integer keyword){
        return taskRepository.showApplicants(keyword);
    }
}
