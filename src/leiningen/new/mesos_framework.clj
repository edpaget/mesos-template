(ns leiningen.new.mesos-framework
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [clojure.string :as string]
            [leiningen.core.main :as main]))

(def render (renderer "mesos-framework"))

(defn mesos-framework
  "FIXME: write documentation"
  [name]
  (let [data {:name (string/trim name)
              :sanitized (name-to-path name)}]
    (main/info "Generating fresh 'lein new' mesos-framework project.")
    (->files data
             [".gitignore" (render ".gitignore" data)]
             ["LICENSE" (render "LICENSE" data)]
             ["README.md" (render "README.md" data)]
             ["Vagrantfile" (render "Vagrantfile" data)]
             ["diagnose-vagrant-setup.sh" (render "diagnose-vagrant-setup.sh" data)]
             ["project.clj" (render "project.clj" data)]
             ["dev/user.clj" (render "dev/user.clj" data)]
             ["src/{{sanitized}}/component/executor_driver.clj" (render "component/executor_driver.clj" data)]
             ["src/{{sanitized}}/component/scheduler_driver.clj" (render "component/scheduler_driver.clj" data)]
             ["src/{{sanitized}}/component/scheduler.clj" (render "component/scheduler.clj" data)]
             ["src/{{sanitized}}/scheduler.clj" (render "scheduler.clj" data)]
             ["src/{{sanitized}}/executor.clj" (render "executor.clj" data)]
             ["src/{{sanitized}}/system.clj" (render "system.clj" data)])))
