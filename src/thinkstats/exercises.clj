(ns thinkstats.exercises)

(defmacro ex [id]
  (let [module (str "thinkstats.exercises." (str id))
        rqsym  (symbol module)
        fnsym  (symbol (str module "/-main"))]
    (do
      (require rqsym)
      `(do (println (str "--------> " '~id))
           (~fnsym)))))

(defn -main []
  (ex ex-1-3)
  (ex ex-2-1)
  (ex ex-2-3)
  (ex ex-histogram)
  (ex ex-2-4)
  (ex ex-2-5)
  (ex ex-2-6)
  (ex ex-2-8)
  (ex ex-2-7)
  (ex ex-3-1)
  (ex ex-3-2)
  (ex ex-3-3)
  (ex ex-3-4)
  (ex ex-3-5)
  (ex ex-3-6))

