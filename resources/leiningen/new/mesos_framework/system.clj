(ns {{name}}.system
  (:require [com.stuartsierra.component :as component]
            [{{name}}.component.executor-driver :refer [new-executor-driver]]
            [{{name}}.component.scheduler-driver :refer [new-scheduler-driver]]
            [{{name}}.component.leader-driver :refer [new-leader-driver]]
            [{{name}}.component.curator :refer [new-curator]]
            [{{name}}.component.scheduler :refer [new-scheduler]]
            [{{name}}.executor :refer [executor]]
            [{{name}}.scheduler :refer [scheduler] :as sched])
  (:gen-class))

(defn executor-system
  []
  (component/system-map
   :driver (new-executor-driver (executor))))

(defn scheduler-system
  [master n-tasks task-launcher]
  (component/system-map
   :scheduler (new-scheduler n-tasks task-launcher)
   :driver (component/using
            (new-scheduler-driver master)
            [:scheduler])))

(defn ha-scheduler-system
  [master n-tasks exhibitor zk-path task-launcher]
  (component/system-map
   :curator (new-curator exhibitor)
   :scheduler (new-scheduler n-tasks task-launcher)
   :leader-driver (component/using
                   (new-leader-driver zk-path master "{{name}}" "{{name}}")
                   [:curator :scheduler])))

(defn -main
  [command-type & [scheduler-type master n-tasks & _]]
  (let [system (condp = [command-type scheduler-type]
                 ["scheduler" "jar"] (scheduler-system master n-tasks sched/jar-task-info)
                 ["scheduler" "shell"] (scheduler-system master n-tasks sched/shell-task-info)
                 ["scheduler" "docker"] (scheduler-system master n-tasks sched/docker-task-info)
                 ["scheduler" "ha"] (ha-scheduler-system master n-tasks sched/jar-task-info)
                 ["executor" nil] (executor-system))]
    (component/start system)
    (while true
      (Thread/sleep 1000000))))
