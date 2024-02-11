package de.aittr.g_27_shop_project.scheduling;

import de.aittr.g_27_shop_project.domain.jpa.Task;
import de.aittr.g_27_shop_project.services.jpa.JpaProductService;
import de.aittr.g_27_shop_project.services.jpa.TaskService;
import java.time.Instant;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@EnableAsync
public class ScheduleExecutor {

  private static Logger logger = LoggerFactory.getLogger(ScheduleExecutor.class);
  private TaskService taskService;
  private JpaProductService productService;

  public ScheduleExecutor(TaskService taskService, JpaProductService productService) {
    this.taskService = taskService;
    this.productService = productService;
  }
//
//    @Scheduled(fixedDelay = 5000)
//    public void fixedDelayTask() {
//        taskService.createTask("Fixed delay task");
//    }

//    @Scheduled(fixedDelay = 5000)
//    public void fixedDelayTask() {
//        taskService.createTask("Fixed delay task 3000 ms");
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    @Scheduled(fixedDelay = 5000)
//    public void fixedDelayTask() {
//        taskService.createTask("Fixed delay task 7000 ms");
//        try {
//            Thread.sleep(7000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    @Scheduled(fixedRate = 5000)
//    public void fixedRateTask() {
//        taskService.createTask("Fixed rate task 3000 ms");
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    @Scheduled(fixedRate = 5000)
//    public void fixedRateTask() {
//        taskService.createTask("Fixed rate task 7000 ms");
//        try {
//            Thread.sleep(7000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Scheduled(fixedRate = 5000)
//    @Async
//    public void fixedRateAsyncTask() {
//        taskService.createTask("Fixed rate async task 7000 ms");
//        try {
//            Thread.sleep(7000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    @Scheduled(fixedDelay = 5000, initialDelay = 20000)
//    public void initialDelayTask() {
//        taskService.createTask("Initial delay task");
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }

  // 2 часа - 7 200 000 миллисекунд.
  // 7200000 -> PT02H

//    @Scheduled(fixedDelayString = "PT03S")
//    public void anotherDelayFormat() {
//        taskService.createTask("Another delay format task");
//    }

//    @Scheduled(fixedDelayString = "${interval}")
//    public void delayInPropertyTask() {
//        taskService.createTask("Delay in property task");
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }

  // 55 * * * * * - каждую минуту в 55 секунд
  // 0 0 8,10 * * * - каждый день в 8 и в 10 часов
  // 0 15 9-17 * * MON-FRI - в будни с 9 до 17 в 15 минут 0 секунд

//    @Scheduled(cron = "5-10 * * * * *")
//    public void cronExpressionTask() {
//        taskService.createTask("Cron expression task");
//    }

  public static void scheduleTaskExecution1(Task task) {
    TaskScheduler scheduler = new DefaultManagedTaskScheduler();
    scheduler.schedule(() -> logger.info(task.getDescription()),
        new CronTrigger("0,10,20,30,40,50 * * * * *"));
  }

//  public static void scheduleTaskExecution(Task task) {
//    TaskScheduler scheduler = new DefaultManagedTaskScheduler();
//    Instant instant = Instant.now().plusSeconds(30);
//    scheduler.schedule(() -> logger.info(task.getDescription()),
//        instant);
//  }

//Реализовать вывод в консоль каждые 30 секунд списка последних пяти выполненных задач.
// Время выполнения предыдущей задачи не должно влиять на старт следующей.
// Создавать новую задачу и логировать ничего не нужно.
   public static void lastFiveTask(Task task) {
    TaskScheduler fiveTask = new DefaultManagedTaskScheduler();
    int count = 0;
    while (count<5){
      Instant instant = Instant.now().plusSeconds(30);
      fiveTask.schedule(()-> logger.info(task.getDescription()),
          instant);
      count++;
    }
  }
  //Реализовать вывод в консоль последнего добавленного в БД товара.
  // Вывод должен производиться в 15 и 45 секунд каждой минуты.
  // Задача должна быть сохранена в БД.
  // Вывод в консоль должен быть осуществлён через логирование поля description созданной задачи.
  // Пример значения поля description -
  // "Последний добавленный в БД продукт - Банан".
  public static void scheduleTaskSave(Task task) {
    TaskScheduler scheduler = new DefaultManagedTaskScheduler();
    scheduler.schedule(() -> logger.info(task.getDescription()),
        new CronTrigger("15,45 * * * * *"));
  }
}