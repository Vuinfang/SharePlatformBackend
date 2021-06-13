package cn.hogrider.shareplatform.repository;

import cn.hogrider.shareplatform.entity.Application;
import cn.hogrider.shareplatform.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    public List<Application> findByApplicant(String applicant);
//    @Query("delete from Application application where application.taskID = :id")
//    public Integer deleteByTaskID(Integer id);
    @Transactional
    public Integer deleteByTaskID(Integer id);

    Application findByTaskIDAndAndApplicant(Integer taskID, String applicant);
    //==========================
    //for personal - unstarted job
    //find all application of user
    @Query("select task from Application app left join Task task on task.id = app.taskID where app.applicant = :applicant")
    List<Task> findAppsByTaskID(String applicant, Pageable pageable);

    //find total number of application
    @Query("select count(task.id) from Application app left join Task task on task.id = app.taskID where app.applicant = :applicant")
    Integer totalApplication(String applicant);
    //
    @Query("select task from Application app left join Task task on task.id = app.taskID where app.applicant = :applicant and task.title like :keyword")
    public List<Task> findAppByKeyword(String applicant, String keyword, Pageable pageable);

    //find total task number
    @Query("select count(task.id) from Application app left join Task task on task.id = app.taskID where app.applicant = :applicant and task.title like :keyword")
    Integer unstartedTotal(String applicant, String keyword);

}
