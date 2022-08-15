DECLARE
@accomClassId INT = ?,
@selectedDate DATE = ?,
@startDt DATETIME = ?,
@endDt DATETIME = ?,
@los INT = ?

exec dbo.[usp_get_average_of_competitors_rate_from_pace_differential]
@selectedDate,
@startDt,
@endDt,
@accomClassId,
@los