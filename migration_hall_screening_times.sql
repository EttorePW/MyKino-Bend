-- Migration script for hall_screening_times table
-- Run this script in your PostgreSQL database on Render

-- Step 1: Create the new table for screening times
CREATE TABLE IF NOT EXISTS hall_screening_times (
    hall_id BIGINT NOT NULL,
    screening_time VARCHAR(50) NOT NULL,
    CONSTRAINT fk_screening_times_hall FOREIGN KEY (hall_id) REFERENCES hall(hall_id) ON DELETE CASCADE
);

-- Step 2: Migrate existing data from screening_times column (if it exists)
-- This will split the comma-separated values into individual records
DO $$
DECLARE
    hall_record RECORD;
    time_value TEXT;
BEGIN
    -- Check if screening_times column exists
    IF EXISTS (SELECT 1 FROM information_schema.columns 
               WHERE table_name = 'hall' AND column_name = 'screening_times') THEN
        
        -- Loop through each hall that has screening_times data
        FOR hall_record IN 
            SELECT hall_id, screening_times 
            FROM hall 
            WHERE screening_times IS NOT NULL AND screening_times != ''
        LOOP
            -- Split the comma-separated values and insert each one
            FOR time_value IN 
                SELECT TRIM(unnest(string_to_array(hall_record.screening_times, ',')))
            LOOP
                -- Insert each screening time as a separate record
                INSERT INTO hall_screening_times (hall_id, screening_time) 
                VALUES (hall_record.hall_id, time_value)
                ON CONFLICT DO NOTHING; -- Avoid duplicates if script runs multiple times
            END LOOP;
        END LOOP;
        
        -- Drop the old screening_times column after migration
        ALTER TABLE hall DROP COLUMN IF EXISTS screening_times;
    END IF;
END $$;

-- Step 3: Insert sample data if tables are empty
INSERT INTO hall_screening_times (hall_id, screening_time)
SELECT * FROM (VALUES 
    (1, '14:00'),
    (1, '16:30'),
    (1, '19:00'),
    (1, '21:30'),
    (2, '15:00'),
    (2, '17:30'),
    (2, '20:00'),
    (2, '22:30'),
    (3, '13:30'),
    (3, '16:00'),
    (3, '18:30'),
    (3, '21:00')
) AS v(hall_id, screening_time)
WHERE NOT EXISTS (SELECT 1 FROM hall_screening_times WHERE hall_screening_times.hall_id = v.hall_id);

-- Verify the migration
SELECT h.hall_id, h.capacity, h.supported_movie_version, 
       array_agg(hst.screening_time ORDER BY hst.screening_time) as screening_times
FROM hall h
LEFT JOIN hall_screening_times hst ON h.hall_id = hst.hall_id
GROUP BY h.hall_id, h.capacity, h.supported_movie_version
ORDER BY h.hall_id;
