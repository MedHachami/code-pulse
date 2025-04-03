export function formatDate(timestamp: string) {
    // Convert to a format that the Date constructor can parse
    const date = new Date(timestamp.replace('CEST', 'GMT+0200')); // Adjust for timezone if necessary

    // Check if the date is valid
    if (isNaN(date.getTime())) {
        console.error("Invalid date:", timestamp); 
        return 'Invalid date'; 
    }

    // Get hours and minutes
    let hours = date.getHours();
    const minutes = String(date.getMinutes()).padStart(2, "0");

    // Determine AM or PM
    const amOrPm = hours >= 12 ? "PM" : "AM";

    // Convert to 12-hour format
    hours = hours % 12;
    hours = hours ? hours : 12; // Handle midnight

    // Format the date string
    const formattedTime = `${hours}:${minutes} ${amOrPm}`;

    return formattedTime;
}
