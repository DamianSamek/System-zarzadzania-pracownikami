export function PostData(type, userData) {
    let BaseURL = 'http://localhost:8080/';

    return new Promise((resolve, reject) =>{
    
         
        fetch(BaseURL+type, {
            method: 'POST',
            body: JSON.stringify(userData)
          })
          .then((response) => response.json()
          .then((res) => {
            resolve(res);
            localStorage.setItem('token',response.headers.get("Authorization"))
                      })
          .catch((error) => {
            reject(error);
          }));

  
      });
}