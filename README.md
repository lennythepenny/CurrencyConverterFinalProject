# Currency Converter App

## Overview
The Currency Converter App is designed to allow users to convert various currencies. It provides a user-friendly interface with multiple functionalities. This README describes the purpose of each activity and XML layout file used in the app.

## Activities

### 1. MainActivity
**Description**: The main screen of the app where currency conversion takes place.

**Features**:
- Text input field for entering the current currency amount to convert.
- TextView displaying the final currency amount after conversion.
- Dropdown menu to select the current and final types of currency.
- Swap button to quickly exchange the selected currencies.
- Calculate button to perform the currency conversion.
- Clear button to reset input fields.
- Favorites button to navigate to the Favorites Activity.
- Support for multiple languages.
- Currency conversion logic.

**XML Layout File**: `activity_main.xml`

### 2. FavoritesActivity
**Description**: Allows users to save and manage favorite currencies.

**Features**:
- List of favorite currencies.
- Option to add, edit, and remove favorite currencies.
- Navigation back to the main screen.

**XML Layout File**: `activity_favorites.xml`

### 3. SettingsActivity
**Description**: Manages app settings and preferences.

**Features**:
- Ability to change the app's theme.
- Set the default currency when the app starts.
- Preferences for app customization.
- A single activity that combines settings UI and preferences for a cleaner user experience.

**XML Layout File**: `activity_settings.xml` (combined with preferences)

### 4. DisplayInformationActivity
**Description**: Displays information about the currency conversion rates.

**Features**:
- Information about exchange rates.
- Navigation to the main screen.

**XML Layout File**: `activity_display_information.xml`

## XML Layout Files

### 1. `activity_main.xml`
**Description**: Layout for the main screen where currency conversion takes place.

**Components**:
- Text input field.
- TextView for displaying the final currency amount.
- Dropdown menu.
- Swap, Calculate, Clear, and Favorites buttons.

### 2. `activity_favorites.xml`
**Description**: Layout for managing favorite currencies.

**Components**:
- List of favorite currencies.
- Add, edit, and remove options.
- Back button for navigation.

### 3. `activity_settings.xml`
**Description**: Layout that combines UI for app settings and preferences.

**Components**:
- Preferences for app theme and default currency.

