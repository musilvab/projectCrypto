document.getElementById("loginForm").addEventListener("submit", async function(event) {
    event.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const formData = new URLSearchParams();
    formData.append("username", username);
    formData.append("password", password);

    try {
        const response = await fetch("http://localhost:8080/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: formData
        });

        if (response.ok) {
            alert("Login bem-sucedido!");
            window.location.replace("/home")
        } else {
            alert("Falha no login. Verifique suas credenciais.");
        }
    } catch (error) {
        console.error("Erro:", error);
        alert("Ocorreu um erro. Tente novamente mais tarde.");
    }
});
