<template>
<Navbar :residentAdmin="true" />

<div class="d-flex justify-center">
<h3 class="josefin-sans approved-title">Solicitudes de tu familia</h3>
</div>

<div class="card-container">
    <UserCard
      v-for="(user, index) in users"
      :key="index"
      :name="user.resident"
      :date="user.entryDate || user.multipleCount"
      :relatedPerson="user.visitor"
      :showButton="true"
      :user="user"
      @request-click="handleRequestClick"
    />
</div>

</template>

<script>
import Navbar from '../components/navbar.vue';
import UserCard from '../components/request-card.vue';
import services from '../services';
import Swal from 'sweetalert2';
export default {
components: {
  Navbar,
  UserCard
},
data() {
    return {
      users: [],
      selectedUser: null,
    };
  },
  methods: {
    async getAllAproved(){
      const getAllAproved = await services.residentAdmin.getAllAproved();
      this.users = getAllAproved.data.data ?? [];
    },
    async handleRequestClick(user){
      this.selectedUser = user;
       await this.rejectRequest();
    },
    async rejectRequest(){
      const requestId = this.selectedUser.id
      const residentName = this.selectedUser.resident
      const visitorName = this.selectedUser.visitor
      const status = 'rejected'
      const rejectRequest = await services.residentAdmin.approveRequest(requestId,residentName,visitorName,status);
      if(rejectRequest.status === 200){
        Swal.fire({
          icon: 'success',
          title: 'Solicitud rechazada con exito',
          showConfirmButton: false,
          timer: 1500
        }).then(() => {
          window.location.reload();
        })
      }else{
        Swal.fire({
          icon: 'error',
          title: 'Error al rechazar la solicitud',
          showConfirmButton: false,
          timer: 1500
        })
      }
    }
  },
  created(){
    this.getAllAproved();
  }
}
</script>

<style scoped>

.card-container{
    display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

.approved-title{
width: 85%;
margin-top: 1em;
color: #000;
text-align: center;
font-feature-settings: 'clig' off, 'liga' off;
font-family: "Josefin Sans";
font-size: 32px;
font-style: normal;
font-weight: 600;
line-height: normal;
}


@media (max-width: 768px ) {
   
}
</style>