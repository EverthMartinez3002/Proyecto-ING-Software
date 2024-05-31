<template>
    <div class="card">
      <div class="card-header">
        <h4 class="card-title">Casa # {{ houseNumber }}</h4>
        <h4 class="card-title">{{ ownerName }}</h4>
      </div>
      <div class="card-body">
        <p>{{ address }}</p>
      </div>
      <div class="card-footer">
        <p>Residentes: {{ residents }}</p>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      houseNumber: {
        type: String,
        required: true
      },
      ownerName: {
        type: String,
        required: true
      },
      address: {
        type: String,
        required: true
      },
      residents: {
        type: Number,
        required: true
      }
    }
  }
  </script>
  
  <style scoped>
  .card {
    background-color: #EDF1F4;
    border-radius: 15px;
    padding: 20px;
    margin: 10px;
    width: 300px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }
  
  .card-header {
    display: flex;
    justify-content: space-between;
  }
  
  .card-title {
    font-family: 'Josefin Sans', sans-serif;
    color: #000;
    font-size: 18px;
    margin: 0;
  }
  
  .card-body {
    margin: 10px 0;
  }
  
  .card-footer {
    font-family: 'Josefin Sans', sans-serif;
    color: #000;
    font-size: 14px;
    margin-top: 10px;
  }
  </style>
  