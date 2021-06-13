package cn.hogrider.shareplatform.repository;

import cn.hogrider.shareplatform.entity.FinishedApplication;
import cn.hogrider.shareplatform.entity.FinishedTask;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinishedApplicationRepository extends JpaRepository<FinishedApplication, Integer> {
    List<FinishedApplication> findByApplicant(String keyword);
    //==========================
    //for personal - finished job
    //find all application of user
    @Query("select task from FinishedApplication app left join FinishedTask task on task.id = app.taskID where app.applicant = :applicant")
    List<FinishedTask> findAppsByTaskID(String applicant, Pageable pageable);

    //find total number of application
    @Query("select count(task.id) from FinishedApplication app left join FinishedTask task on task.id = app.taskID where app.applicant = :applicant")
    Integer totalApplication(String applicant);
    //
    @Query("select task from FinishedApplication app left join FinishedTask task on task.id = app.taskID where app.applicant = :applicant and task.title like :keyword")
    public List<FinishedTask> findAppByKeyword(String applicant, String keyword, Pageable pageable);

    //find total task number
    @Query("select count(task.id) from FinishedApplication app left join FinishedTask task on task.id = app.taskID where app.applicant = :applicant and task.title like :keyword")
    Integer unstartedTotal(String applicant, String keyword);
}
