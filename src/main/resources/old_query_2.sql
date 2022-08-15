 select cast(wr.Webrate_GenerationDate_temp AS DATE) AS Occupancy_DT, wr.Webrate_Channel_ID, wr.Webrate_Competitors_ID, wr.Webrate_RateValue_Display 
                 into #pace_webrate_Differential 
                 FROM 
                 ( 
                      select wr.*,calendar_date as webrate_generationDate_temp from PACE_Webrate_Differential as wr 
                              cross join calendar_dim cal 
                              WHERE wr.Occupancy_DT = ? AND wr.LOS= ? AND wr.Webrate_Status = 'A' 
                              and  cast(cal.calendar_date AS date)  between Convert(date,First_webrate_generationDate) and Convert(date,webrate_generationDate) 
                      and cal.calendar_date between ? and ?
                 )AS wr 
                 INNER JOIN Webrate_Accom_Class_Mapping wacm ON wr.Webrate_Accom_Type_ID = wacm.Webrate_Accom_Type_ID and wacm.Accom_Class_ID= ?
                 INNER JOIN Webrate_Competitors AS competitors ON wr.Webrate_Competitors_ID = competitors.Webrate_Competitors_ID and competitors.Status_ID = 1 
                 INNER JOIN Webrate_Competitors_Class AS  wcc ON wcc.Webrate_Competitors_ID = wr.Webrate_Competitors_ID and wcc.Demand_Enabled = 1 and wcc.Accom_Class_ID = ? and wcc.Product_ID = 1

                 SELECT result.Occupancy_DT AS Occupancy_DT, avg(result.rate) AS rate 
                 FROM 
                 ( 
                       SELECT wr.Occupancy_DT, min(wr.Webrate_RateValue_Display) AS rate, wr.Webrate_Channel_ID, wr.Webrate_Competitors_ID 
                       FROM #pace_webrate_Differential as wr 
                       INNER JOIN 
                       ( 
                               select  isnull(webr.Monday_override_channel_id,  (select Monday_Channel_ID from Webrate_Default_Channel WHERE Product_ID = 1)) as channel_id,  cast(cal.calendar_date as date) as Webrate_GenerationDate 
                               FROM calendar_dim AS cal left join Webrate_Override_Channel AS webr 
                               ON  webr.Channel_Override_Start_DT <=  cal.calendar_date and cal.calendar_date <= webr.Channel_Override_End_DT and webr.Product_ID = 1
                               WHERE cast(cal.calendar_date AS date) between ? and ?
                       ) as channelbyDate 
                       ON wr.Occupancy_DT = channelbyDate.Webrate_GenerationDate and wr.Webrate_Channel_ID = channelbyDate.channel_id 
                       GROUP BY wr.Occupancy_DT, wr.Webrate_Channel_ID, wr.Webrate_Competitors_ID 
                 ) as result
                 group by result.Occupancy_DT 
                 ORDER BY Occupancy_DT DESC;
