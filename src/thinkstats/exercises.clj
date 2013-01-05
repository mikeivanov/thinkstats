(ns thinkstats.exercises
  (require [clojure.string :as string]))

(defn call [name & args]
    (when-let [fun (ns-resolve *ns* (symbol name))]
      (apply fun args)))

(defn exercise [id]
  (let [path (str "src/thinkstats/exercises/ex_" (string/replace id #"-" "_") ".clj")
        name (str "thinkstats.exercises.ex-" id "/-main")]
    (do
      (load-file path)
      (println (str "--------> " id))
      (call name))))

(def exercises ["1-3" "2-1" "2-3" "histogram" "2-4" "2-5" "2-6" "2-7" "2-8"
                "3-1" "3-2" "3-3" "3-4" "3-5" "3-6" "3-7" "3-8" "3-9" "3-10"
                "4-1" "4-2"])

(defn -main []
  (doseq [id exercises]
    (exercise id)))
