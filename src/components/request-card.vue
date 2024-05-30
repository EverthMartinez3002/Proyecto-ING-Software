<!-- UserCard.vue -->
<template>
    <div class="card">
      <span class="span-card">{{ name }}</span>
      <span class="span-card">{{ date }}</span>
      <span class="span-card-person">{{ relatedPerson }}</span>
      <v-btn v-if="showButton" class="btn-revocar">
      <span style="text-transform: none; font-size: 19px; margin-right: 2em; color: #EDF1F4;" >Revocar</span>
      <img src="/src/assets/img/revocar.svg" class="revocar-icon"/>
    </v-btn>
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
      },
      showButton: {
        type: Boolean,
        default: false
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

  .btn-revocar {
    color: #EDF1F4;
  width: 184px;
  height: 62px;
  background: #A1251B;
  border-radius: 20px;
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
}

.btn-revocar span {
  font-family: 'Josefin Sans', sans-serif;
  font-size: 18px;
}

.revocar-icon{
position: absolute;
  right: 15px;
  top: 50%;
  transform: translateY(-50%);
  height: 20px;
}

  @media (max-width: 768px) {
  .card{
    width: auto;
    flex-wrap: wrap;
    display: flex;
    justify-content: center;
    align-items: center;
    align-items: flex-start;
    height: auto;
  }

  .span-card{
    padding: 0 1em 0 0;
  }

  .span-card-person{
    margin-top: 1.5em;
    margin-right: 1em;

  }

  .btn-revocar {
    margin-top: 10px;
    width: 55%;
    justify-content: center;
  }

}
  </style>
  