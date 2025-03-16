import random
from datetime import datetime, timedelta

# Define meal types
meal_types = ["BREAKFAST", "LUNCH", "DINNER"]

# Define names (you can add more names if needed)
names = ["redowan", "alice", "bob", "charlie", "diana", "eve", "frank", "grace"]

# Get today's date
today = datetime.today()

# Open the file to write
with open("purchasehistory.txt", "w") as file:
    # Loop through the last 13 months
    for month in range(13):
        # Calculate the start date for the current month
        start_of_month = today.replace(day=1) - timedelta(days=30 * month)
        
        # Loop through each day in the month
        for day in range(31):  # Assume up to 31 days in a month
            # Calculate the date
            date = start_of_month - timedelta(days=day)
            date_str = date.strftime("%Y-%m-%d")
            
            # Generate multiple entries per day (randomly between 3 and 10)
            num_entries = random.randint(3, 10)
            for _ in range(num_entries):
                name = random.choice(names)
                meal_type = random.choice(meal_types)
                # Write to file
                file.write(f"{name},{meal_type},{date_str}\n")