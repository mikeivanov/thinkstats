(ns thinkstats.exercises
  (:require [thinkstats.exercises ex-1-3 ex-2-1]))

(defmacro ex [id]
  (let [module (str "thinkstats.exercises." (str id))
        rqsym  (symbol module)
        fnsym  (symbol (str module "/-main"))]
    `(do (println (str "--------> " '~id))
         (require '~rqsym)
         (~fnsym))))

(defn -main []
  (ex ex-1-3)
  (ex ex-2-1)
  (ex ex-2-3)
  (ex ex-histogram)
  (ex ex-2-4)
  (ex ex-2-5))

