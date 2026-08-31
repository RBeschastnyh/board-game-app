package main

import (
    "fmt"
    "log"
    "net/http"
)

func handler(writer http.ResponseWriter, request *http.Request) {
    fmt.Fprintln(writer, "Stub adapter!")
}

func main() {
    http.HandleFunc("/adapter", handler)
    fmt.Println("Starting stub on localhost:8081")

    err := http.ListenAndServe(":8081", nil)

    if err != nil {
        log.Fatal("Error occurred")
    }
}