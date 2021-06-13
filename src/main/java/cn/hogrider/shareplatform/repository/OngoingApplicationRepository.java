package cn.hogrider.shareplatform.repository;

import cn.hogrider.shareplatform.entity.OngoingApplication;
import cn.hogrider.shareplatform.entity.OngoingTask;
import cn.hogrider.shareplatform.entity.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OngoingApplicationRepository extends JpaRepository<OngoingApplication, Integer> {
    public Optional<OngoingApplication> findByTaskID(Integer id);

    List<OngoingApplication> findByApplicant(String keyword);

    //==========================
    //for personal - ongoing job
    //find all application of user
    @Query("select task from OngoingApplication app left join OngoingTask task on task.id = app.taskID where app.applicant = :applicant")
    List<OngoingTask> findAppsByTaskID(String applicant, Pageable pageable);

    //find total number of application
    @Query("select count(task.id) from OngoingApplication app left join OngoingTask task on task.id = app.taskID where app.applicant = :applicant")
    Integer totalApplication(String applicant);
    //
    @Query("select task from OngoingApplication app left join OngoingTask task on task.id = app.taskID where app.applicant = :applicant and task.title like :keyword")
    public List<OngoingTask> findAppByKeyword(String applicant, String keyword, Pageable pageable);

    //find total task number
    @Query("select count(task.id) from OngoingApplication app left join OngoingTask task on task.id = app.taskID where app.applicant = :applicant and task.title like :keyword")
    Integer unstartedTotal(String applicant, String keyword);
}
