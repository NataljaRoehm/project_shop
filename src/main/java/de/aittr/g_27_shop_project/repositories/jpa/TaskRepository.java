package de.aittr.g_27_shop_project.repositories.jpa;

import de.aittr.g_27_shop_project.domain.jpa.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Integer> {

  @Query(value = "SELECT * from task order by id desc limit 5 ;", nativeQuery = true)
  List<Task> lastFiveTask();

}