Overview:
This is a Java-based headless system designed for a university cafeteria setting, where students can purchase meal tokens using a SmartWallet. The system enables students to recharge their wallets, buy meal tokens, and view available menus, while admins handle wallet recharge approvals, manage menus, and analyze meal sales trends using graphical analytics.

Key Features:
Student Features:
SmartWallet System: Students can recharge their wallets by submitting a recharge request for admin approval.
Token Purchase: Students can use their SmartWallet balance to buy meal tokens.
Menu Viewing: Students can view the available menu before purchasing.
Login & Registration: Secure student authentication and profile creation.
Admin Features:
Wallet Recharge Approvals: Admins verify and approve student wallet recharge requests.
Menu Management: Admins can update, add, or remove menu items.
Meal Sales Analytics:
Daily Meal Sales for the Past 7 Days (Graphical View).
Week-wise Sales Trends (Graph with multiple weeks on x-axis, total sales on y-axis).
Month-wise Sales Trends (Graph with 12 months on x-axis, total sales on y-axis).
Meal-wise Breakdown for Each Month.
Execution Flow & Design Principles:
Terminal-based UI with a clean and modular navigation flow.
Hierarchical Nesting of Options for intuitive user interaction.
Adheres to the Single Responsibility Principle (SRP).
Designed for Maintainability & Scalability.
Tools & Technologies Used:
Programming Language: Java
Data Storage: Text files (due to project constraints)
Libraries Used:
Maximum of 5 libraries as per project restriction.
Potentially used libraries: java.io for file handling, java.util for collections, etc.
Graph Implementation: Graphical representation using asterisks (*) in a terminal-friendly format.
Possible Corner Cases Considered:
Insufficient wallet balance when purchasing a token.
Duplicate wallet recharge requests.
Admin forgetting to update the menu.
Students trying to purchase an unavailable meal.
Handling incorrect user inputs gracefully.
