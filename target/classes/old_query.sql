select
    Occupancy_DT,
    SUM(Solds)
from
    (
        select
            Occupancy_DT,
            Forecast_Group_ID,
            Accom_Class_ID,
            SUM(Solds) Solds
        from
            (
                select
                    GB.Occupancy_DT,
                    MSFG.Forecast_Group_ID,
                    AC.Accom_Class_ID,
                    (GB.Blocks - GB.Pickup) Solds
                from
                    Group_Block GB
                        join Group_Master GM on GB.Group_ID = GM.Group_ID
                        AND GM.Group_Status_Code in('DEFINITE')
                        join Accom_Type AT on AT.Accom_Type_ID = GB.Accom_Type_ID
                    and AT.Status_ID not in(6)
                    and AT.Display_Status_ID not in(4)
                    join Accom_Class AC on AT.Accom_Class_ID = AC.Accom_Class_ID
                    join Mkt_Seg_Forecast_Group MSFG on MSFG.Mkt_Seg_ID = GM.Mkt_Seg_ID
                where
                    GB.Occupancy_DT between ?
                  and ?
                  and MSFG.Status_ID = 1
                  and GB.Occupancy_DT > ?
            ) A
        group by
            Occupancy_DT,
            Forecast_Group_ID,
            Accom_Class_ID
        union all
        select
            RN.Occupancy_DT,
            MSFG.Forecast_Group_ID,
            AC.Accom_Class_ID,
            count(*) Solds
        from
            Reservation_Night RN
                join Accom_Type AT on RN.Booked_Accom_Type_Code = AT.Accom_Type_Code
            and AT.Status_ID not in(6)
            and AT.Display_Status_ID not in(4)
            join Accom_Class AC on AC.Accom_Class_ID = AT.Accom_Class_ID
            join Mkt_Seg_Forecast_Group MSFG on MSFG.Mkt_Seg_ID = RN.Mkt_Seg_ID
        where
            RN.Occupancy_DT between ?
          and ?
          and MSFG.Status_ID = 1
          and RN.Individual_Status NOT IN ('XX', 'NS', 'CANCELLED', 'NO_SHOW', 'NO SHOW')
          AND RN.Arrival_DT != RN.Departure_DT
        group by
            RN.Occupancy_DT,
            MSFG.Forecast_Group_ID,
            AC.Accom_Class_ID
    ) A
where
    1 = 1
group by
    Occupancy_DT
order by
    Occupancy_DT desc
