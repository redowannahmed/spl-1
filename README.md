Token-Table
Token-Table is a Java console-based application designed to streamline the meal token management system for university students. It provides a digital smart-wallet experience, allowing students to request wallet recharges, purchase meal tokens, and manage their dining transactions efficiently. Admins can verify recharge requests, update meal menus, and view detailed analytics on meal token sales.

Table of Contents
Features

Technologies Used

Installation

Usage

Folder Structure

Solution Approach

Challenges Faced

Lessons Learned

Features
Student Panel
Request wallet recharge (generates a unique SLIP-ID)

View recharge request status

View current wallet balance

Buy meal tokens (Breakfast, Lunch, Dinner) and generate E-token

View personal purchase history

View static weekly meal menu

Update profile information (username, password)

Admin Panel
View and process recharge requests (validating SLIP-ID against a simulated database)

Approve or decline recharge requests

Update meal menu for specific days

View weekly and monthly sales analytics with simple terminal graphs

Technologies Used
Programming Language: Java

Persistence: Text files (purchaseHistory.txt, rechargeRequests.txt, studentWallet.txt, etc.)

Version Control: Git and GitHub

Development Tools: Visual Studio Code and IntelliJ IDEA

No external libraries were used. All features are implemented using native Java libraries (I/O, Collections, DateTime, etc.).

Installation
Clone the repository:

bash
Copy
Edit
git clone https://github.com/your-username/token-table.git
Open the project in Visual Studio Code or IntelliJ IDEA.

Ensure Java 11+ is installed.

Compile and run the project using the terminal or your IDE’s run options.

Usage
Run the application.

Login as a student to:

Request a recharge (get SLIP-ID).

Buy meal tokens using wallet balance.

View wallet balance and purchase history.

See the static weekly meal menu.

Login as an admin to:

View pending recharge requests.

Approve/decline requests after SLIP-ID validation.

Update weekly menu items.

View rolling 4-week or monthly/yearly sales analytics.

All actions are navigated through a simple terminal-based menu.

Folder Structure
bash
Copy
Edit
src/
├── analytics/       # Admin analytics and reporting features
├── auth/            # Authentication-related classes
├── token/           # Token buying and purchase management
├── UI/              # Console UI helpers (menu display, messages)
├── user/            # Student and Admin classes
├── wallet/          # Wallet management and recharge approval
├── purchaseHistory.txt   # Stores purchase logs
├── rechargeRequests.txt  # Stores pending recharge requests
├── paymentLog.txt        # Stores payment verification logs
├── studentWallet.txt     # Stores student wallet balances
Solution Approach
Wallet Recharge and Approval:
Students submit recharge requests and receive a unique SLIP-ID. Admins verify SLIP-IDs against a simulated payment log. If valid, the recharge is approved and the wallet balance is updated.

Meal Token Purchase and E-token Generation:
Students purchase meal tokens based on their balance. Upon purchase, the system deducts the correct amount, updates the balance file, and logs the transaction with timestamp details.

Sales Analytics:
Admins can view weekly and monthly meal sales analytics generated from transaction logs. The system parses and organizes transaction dates into intervals and displays simple graphs inside the terminal.

Challenges Faced
Early planning and collaboration:
Without a standardized initial file structure, integrating different modules later became slightly challenging.

Implementing real-world features:
Simulating payment verification using text files and maintaining recharge integrity required careful thought.

Handling edge cases:
Detecting duplicate SLIP-IDs, handling invalid inputs, and ensuring data consistency between wallet updates and purchase records.

Learning version control workflows:
Using Git and GitHub in a team setting (handling merges, pull conflicts, and syncs) was a learning curve.

Building analytics manually:
Designing rolling-week and monthly sales reporting from scratch without external libraries was complex but rewarding.

Lessons Learned
Through this project, we learned the critical importance of planning both user experience and system architecture from the beginning. Organizing features cleanly even for a console application made development much smoother.
We gained hands-on experience in file-based data persistence, version control workflows, and handling real-world transaction simulations.
Designing simple analytics and visual outputs in a text-based environment also taught us the value of clear data presentation.
Finally, this project helped us meaningfully apply object-oriented programming principles and better understand how a software system evolves from an idea to a complete, working solution.

📚 End of Documentation
Would you also want me to quickly show you how a very minimal Project Description banner could look at the top (for extra polish)? 🚀
Example:

markdown
Copy
Edit
> Simple console-based Smart Wallet and Meal Token Management System for university students. 🚀  
> Built with Java. No external dependencies.
Let me know! 🔥







