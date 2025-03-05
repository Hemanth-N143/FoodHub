document.addEventListener("DOMContentLoaded", () => {
  // Time format functions
  const formatRelativeTime = (date) => {
    const now = new Date()
    const diffInSeconds = Math.floor((now - date) / 1000)
    const diffInMinutes = Math.floor(diffInSeconds / 60)
    const diffInHours = Math.floor(diffInMinutes / 60)
    const diffInDays = Math.floor(diffInHours / 24)

    if (diffInDays > 7) {
      return date.toLocaleDateString(navigator.language, {
        year: "numeric",
        month: "short",
        day: "numeric",
      })
    } else if (diffInDays > 0) {
      return `${diffInDays} day${diffInDays > 1 ? "s" : ""} ago`
    } else if (diffInHours > 0) {
      return `${diffInHours} hour${diffInHours > 1 ? "s" : ""} ago`
    } else if (diffInMinutes > 0) {
      return `${diffInMinutes} minute${diffInMinutes > 1 ? "s" : ""} ago`
    } else {
      return "Just now"
    }
  }

  const formatDateTime = (date) => {
    return date.toLocaleString(navigator.language, {
      year: "numeric",
      month: "long",
      day: "numeric",
      hour: "2-digit",
      minute: "2-digit",
    })
  }

  // Parse dates
  const createdAt = new Date(document.getElementById("accountCreated").innerText.replace(" ", "T"))
  const lastLogin = new Date(document.getElementById("lastLogin").innerText.replace(" ", "T"))

  // Update account timestamps
  document.getElementById("lastLogin").textContent = `Last login: ${formatRelativeTime(lastLogin)}`
  document.getElementById("lastLogin").title = formatDateTime(lastLogin)

  document.getElementById("accountCreated").textContent = `Account created: ${formatDateTime(createdAt)}`

  // Member since message
  const monthNames = [
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December",
  ]
  const memberSinceMessage = `Member since ${monthNames[createdAt.getMonth()]} ${createdAt.getFullYear()}`
  document.getElementById("member-since").textContent = memberSinceMessage

  // Format recent order dates
  const orderDateElements = document.querySelectorAll(".recent-order-date")
  orderDateElements.forEach((element) => {
    const orderDate = new Date(element.textContent.trim())
    element.textContent = formatRelativeTime(orderDate)
    element.title = formatDateTime(orderDate)
  })

  // Add hover effect to order items
  const orderItems = document.querySelectorAll(".order-item")
  orderItems.forEach((item) => {
    item.addEventListener("mouseenter", () => {
      item.style.transform = "translateX(10px)"
      item.style.boxShadow = "0 4px 6px rgba(0, 0, 0, 0.1)"
    })
    item.addEventListener("mouseleave", () => {
      item.style.transform = "translateX(0)"
      item.style.boxShadow = "none"
    })
  })

  // Add animation to stats
  const stats = document.querySelectorAll(".stat")
  stats.forEach((stat, index) => {
    stat.style.opacity = "0"
    stat.style.transform = "translateY(20px)"
    setTimeout(() => {
      stat.style.transition = "opacity 0.5s, transform 0.5s"
      stat.style.opacity = "1"
      stat.style.transform = "translateY(0)"
    }, 100 * index)
  })

  // Lazy load profile image
  const profileImage = document.querySelector(".profile-image")
  if (profileImage) {
    profileImage.addEventListener("load", () => {
      profileImage.style.opacity = "1"
    })
    profileImage.src = profileImage.dataset.src
  }
})