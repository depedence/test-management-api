const form = document.querySelector("[data-auth]")
const message = document.getElementById("message")

form.addEventListener("submit", async (event) => {
    event.preventDefault()

    const data = new FormData(form)
    const payload = Object.fromEntries(data.entries())

    const response = await fetch(form.action, {
        method: "POST",
        headers: { "Content-Type": "application/json"},
        body: JSON.stringify(payload)
    })

    const result = await response.json()

    if (!response.ok) {
        message.textContent = result.error || "Ошибка"
        return
    }

    if (form.dataset.auth === "register") {
        window.location.href = "/login"
    } else {
        window.location.reload()
        window.location.href = "/home"
    }
})