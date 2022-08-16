DECLARE
@startDate DATETIME = ?,
@endDate DATETIME = ?,
@snapshotDate DATETIME = ?,
@forecastGroupId INT = ?,
@accomClassId INT = ?

exec dbo.[usp_get_occupancy_on_books_for_booked_data]
@startDate,
@endDate,
@snapshotDate,
@forecastGroupId,
@accomClassId