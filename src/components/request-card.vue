<!-- UserCard.vue -->
<template>
    <div class="card">
      <span>{{ name }}</span>
      <span>{{ date }}</span>
      <span>{{ relatedPerson }}</span>
    </div>
  </template>
  
  <script>
  export default {
    name: "UserCard",
    props: {
      name: {
        type: String,
        required: true
      },
      date: {
        type: String,
        required: true
      },
      relatedPerson: {
        type: String,
        required: true
      }
    }
  };
  </script>
  
  <style scoped>
  .card {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #f6f9fb;
    border-radius: 20px;
    padding: 20px;
    width: 816px;
    height: 166px;
    margin: 10px 0;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  }
  
  .card span {
    font-family: 'Josefin Sans', sans-serif;
    font-size: 18px;
    color: #171616;
  }

  @media (max-width: 768px) {
  .card{
    width: auto;
    flex-wrap: wrap;
    display: flex;
    justify-content: center;
    align-items: center;
    align-items: flex-start;
    height: 130px;
  }

  span{
    padding: 0 1em 0 0;
  }

}
  </style>
  