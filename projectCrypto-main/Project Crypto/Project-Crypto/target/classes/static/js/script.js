function fetchData(endpoint) {
    const token = localStorage.getItem("jwtToken");

    if (!token) {
        alert("Token JWT não encontrado. Redirecionando para o login...");
        window.location.href = "/auth/login";
        return;
    }

    fetch(endpoint, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro ao acessar os dados.");
            }
            return response.json();
        })
        .then(data => {
            console.log("Dados recebidos:", data);
            // Aqui você pode fazer algo com os dados, como atualizá-los na página
        })
        .catch(error => {
            console.error("Erro ao buscar os dados:", error);
        });
}
