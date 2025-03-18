# Token-Based Meal Purchase System  

## Overview  
This is a **Java-based headless system** designed for a **university cafeteria setting**, where students can purchase meal tokens using a **SmartWallet**.  

The system enables:  
- **Students** to recharge wallets, buy meal tokens, and view available menus.  
- **Admins** to approve wallet recharges, manage menus, and analyze meal sales trends using graphical analytics.  

---

## Features  

### 🎓 Student Features  
✅ **SmartWallet System** – Students can submit recharge requests for admin approval.  
✅ **Token Purchase** – Buy meal tokens using wallet balance.  
✅ **Menu Viewing** – View available meals before purchasing.  
✅ **Login & Registration** – Secure student authentication.  

### 🛠️ Admin Features  
✅ **Wallet Recharge Approvals** – Verify and approve student recharge requests.  
✅ **Menu Management** – Update, add, or remove menu items.  
✅ **Meal Sales Analytics** (Graphical Representation):  
   - 📅 **Daily Meal Sales for the Past 7 Days**  
   - 📈 **Week-wise Sales Trends** (Weeks on x-axis, total sales on y-axis)  
   - 📊 **Month-wise Sales Trends** (12 months on x-axis, total sales on y-axis)  
   - 🍽️ **Meal-wise Breakdown for Each Month**  

---

## 🏗️ Execution Flow & Design Principles  
- **Terminal-based UI** with a clean and modular navigation flow.  
- **Hierarchical Nesting of Options** for intuitive user interaction.  
- **Adheres to the Single Responsibility Principle (SRP).**  
- **Designed for Maintainability & Scalability.**  

---

## 🛠️ Tools & Technologies Used  
- **Programming Language:** Java ☕  
- **Data Storage:** Text files (due to project constraints)  
- **Libraries Used:** (Max **5 libraries** as per project restriction)  
  - `java.io` – File handling  
  - `java.util` – Collections, data structures  
  - Other required utilities  

- **Graph Implementation:**  
  📌 Graphical representation using **asterisks (`*`)** in a **terminal-friendly** format.  

---

## 🔍 Possible Corner Cases Considered  
❌ **Insufficient Wallet Balance** – Prevents token purchase if balance is too low.  
❌ **Duplicate Recharge Requests** – Ensures no duplicate requests.  
❌ **Admin Menu Updates** – Prevents students from ordering unavailable meals.  
❌ **Incorrect User Inputs** – Handles errors gracefully to prevent crashes.  

---

🚀 **Built for SPL-1 Project | University Cafeteria Management System**  
