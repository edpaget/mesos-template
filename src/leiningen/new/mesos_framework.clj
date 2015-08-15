(ns leiningen.new.mesos-framework
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "mesos-framework"))

(defn mesos-framework
  "FIXME: write documentation"
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)}]
    (main/info "Generating fresh 'lein new' mesos-framework project.")
    (->files data
             [".gitignore" (render "gitignore" data)]
             ["LICENSE" (render "LICENSE" data)]
             ["README.md" (render "README.md" data)]
             ["Vagrantfile" (render "Vagrantfile" data)]
             ["project.clj" (render "project.clj" data)]
             ["dev/user.clj" (render "dev/user.clj" data)]
             ["src/{{sanitized}}/components/executor.clj" (render "components/executor.clj" data)]
             ["src/{{sanitized}}/components/scheduler.clj" (render "components/scheduler.clj" data)]
             ["src/{{sanitized}}/system.clj" (render "system.clj" data)])))