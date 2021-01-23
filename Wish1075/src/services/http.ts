// export async function http<T>(
//     request: RequestInfo
//   ): Promise<T> {
//     let response: Promise<T> = await fetch(
//       request
//     );
  
//     try {
//       // may error if there is no body
//       response.parsedBody = await response.json();
//     } catch (ex) {}
  
//     if (!response.ok) {
//       throw new Error(response.statusText);
//     }
//     return response;
//   }

export async function http<T>(
    request: RequestInfo
): Promise<T> {
    const response = await fetch(request);
    const body = await response.json();
    return body;
}

export async function get<T>(
    path: string,
    args: RequestInit = { method: "get" }
): Promise<T> {
    return await http<T>(new Request(path, args));
};

export async function post<T>(
    path: string,
    body: any,
    args: RequestInit = { method: "post", body: JSON.stringify(body) }
): Promise<T>  {
    return await http<T>(new Request(path, args));
};

export async function put<T>(
    path: string,
    body: any,
    args: RequestInit = { method: "put", body: JSON.stringify(body) }
): Promise<T> {
    return await http<T>(new Request(path, args));
};

  
// example consuming code
//   const response = await post<{ id: number }>(
//     "https://jsonplaceholder.typicode.com/posts",
//     { title: "my post", body: "some content" }
//   );