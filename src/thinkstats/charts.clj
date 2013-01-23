(ns thinkstats.charts
  (:import org.jfree.chart.axis.LogAxis))

(defn log-scale-x [chart]
  (let [plot (.getPlot chart)
        axis (LogAxis.)]
    (.setDomainAxis plot 0 axis))
  chart)

(defn log-scale-y [chart]
  (let [plot (.getPlot chart)
        axis (LogAxis.)]
    (.setRangeAxis plot 0 axis))
  chart)
