<template>
  <div class="navbar">
    <img src="/src/assets/img/logo_HLVS.png" alt="logo_HLVS" class="img-logo">

    <div class="hamburger" @click="toggleMenu" v-if="isMobile">
      <div :class="{'bar': true, 'bar1': true, 'change-bar1': isMenuOpen}"></div>
      <div :class="{'bar': true, 'bar2': true, 'change-bar2': isMenuOpen}"></div>
      <div :class="{'bar': true, 'bar3': true, 'change-bar3': isMenuOpen}"></div>
    </div>

    <div class="d-flex" v-if="!isMobile && residentAdmin">
      <h4 class="josefin-sans-light" style="cursor: pointer;" @click="redirectToFamily()">Familia</h4>
      <h4 class="josefin-sans-light" style="cursor: pointer;" @click="redirectToQr()">Generar QR</h4>
      <h4 class="josefin-sans-light" style="cursor: pointer;" @click="redirectToRequest()">Solicitudes</h4>
      <h4 class="josefin-sans-light" style="cursor: pointer;" @click="redirectToHistory()">Historial</h4>
    </div>

    <div class="d-flex" v-if="!isMobile && admin">
      <h4 class="josefin-sans-light" style="cursor: pointer;" @click="redirectToHomeManagement()">Hogares</h4>
      <h4 class="josefin-sans-light" style="cursor: pointer;" @click="redirectToHistoryEntries()">Historial</h4>
      <h4 class="josefin-sans-light" style="cursor: pointer;" @click="redirectoToDevices()">Dispositivos y QR</h4>
    </div>

    <div class="d-flex" v-if="!isMobile && resident">
      <h4 class="josefin-sans-light" style="cursor: pointer;" @click="redirectToQr()">Generar QR</h4>
      <h4 class="josefin-sans-light" style="cursor: pointer;" @click="redirectToStateRequest()">Solicitudes</h4>
      <h4 class="josefin-sans-light" style="cursor: pointer;" @click="redirectToHistory()">Historial</h4>
    </div>

    <div class="d-flex" v-if="!isMobile && visitor">
      <h4 class="josefin-sans-light" style="cursor: pointer;" @click="redirectToQr()">Generar QR</h4>
      <h4 class="josefin-sans-light" style="cursor: pointer;" @click="redirectToHistory()">Historial</h4>
    </div>

    <!-- Información de usuario para diferentes roles -->
    <div class="user-info" v-if="!isMobile && (admin || residentAdmin || resident || visitor)">
      <h4 class="josefin-sans-light" @click="toggleUserMenu">{{ nameGoogle }}</h4>
      <img :src="pictureGoogle" alt="google_image" class="google-image" @click="toggleUserMenu">
      <div v-if="isUserMenuOpen" class="user-dropdown">
        <img :src="pictureGoogle" alt="google_image" class="google-image-dropdown">
        <p>{{ nameGoogle }}</p>
        
        <button @click="logout">Cerrar sesión</button>
      </div>
    </div>
  </div>

  <!-- Menú móvil -->
  <div :class="{'menu': true, 'menu-open': isMenuOpen}" v-if="residentAdmin">
    <div class="close-menu" @click="toggleMenu">X</div>
    <h4 style="color: #FFF; cursor: pointer;" class="josefin-sans-light" @click="redirectToFamily()">Familia</h4>
    <h4 style="color: #FFF; cursor: pointer;" class="josefin-sans-light" @click="redirectToQr()">Generar QR</h4>
    <h4 style="color: #FFF; cursor: pointer;" class="josefin-sans-light" @click="redirectToRequest()">Solicitudes</h4>
    <h4 style="color: #FFF; cursor: pointer;" class="josefin-sans-light" @click="redirectToHistory()">Historial</h4>
    <h4 style="color: #FFF; cursor: pointer;" class="josefin-sans-light" @click="logout">Cerrar sesión</h4>
  </div>

  <div :class="{'menu': true, 'menu-open': isMenuOpen}" v-if="admin">
    <div class="close-menu" @click="toggleMenu">X</div>
    <h4 style="color: #FFF; cursor: pointer;" class="josefin-sans-light" @click="redirectToHomeManagement()">Hogares</h4>
    <h4 style="color: #FFF; cursor: pointer;" class="josefin-sans-light" @click="redirectToHistoryEntries()">Historial</h4>
    <h4 style="color: #FFF; cursor: pointer;" class="josefin-sans-light" @click="redirectoToDevices()">Dispositivos y QR</h4>
    <h4 style="color: #FFF; cursor: pointer;" class="josefin-sans-light" @click="logout">Cerrar sesión</h4>
  </div>

  <div :class="{'menu': true, 'menu-open': isMenuOpen}" v-if="resident">
    <div class="close-menu" @click="toggleMenu">X</div>
    <h4 class="josefin-sans-light" style="color: #FFF; cursor: pointer;" @click="redirectToQr()">Generar QR</h4>
    <h4 class="josefin-sans-light" style="color: #FFF; cursor: pointer;" @click="redirectToStateRequest()">Solicitudes</h4>
    <h4 class="josefin-sans-light" style="color: #FFF; cursor: pointer;" @click="redirectToHistory()">Historial</h4>
    <h4 style="color: #FFF; cursor: pointer;" class="josefin-sans-light" @click="logout">Cerrar sesión</h4>
  </div>

  <div :class="{'menu': true, 'menu-open': isMenuOpen}" v-if="visitor">
    <div class="close-menu" @click="toggleMenu">X</div>
    <h4 class="josefin-sans-light" style="color: #FFF; cursor: pointer;" @click="redirectToQr()">Generar QR</h4>
    <h4 class="josefin-sans-light" style="color: #FFF; cursor: pointer;" @click="redirectToHistory()">Historial</h4>
    <h4 style="color: #FFF; cursor: pointer;" class="josefin-sans-light" @click="logout">Cerrar sesión</h4>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isMenuOpen: false,
      isMobile: false,
      isUserMenuOpen: false,
      name: '',
      nameGoogle: '',
      pictureGoogle: '',
    }
  },
  props: {
    residentAdmin: {
        type: Boolean,
        required: false
      },
    admin: {
      type: Boolean,
      required: false
    },
    resident: {
      type: Boolean,
      required: false
    },
    guard: {
      type: Boolean,
      required: false
    },
    visitor: {
      type: Boolean,
      required: false
    }
  },
  methods: {
    toggleMenu() {
      this.isMenuOpen = !this.isMenuOpen;
    },
    toggleUserMenu() {
      this.isUserMenuOpen = !this.isUserMenuOpen;
    },
    checkMobile() {
      this.isMobile = window.innerWidth <= 768;
    },
    redirectToFamily() {
      this.$router.push('/family-management');
    },
    redirectToQr() {
      this.$router.push('/qr');
    },
    redirectToRequest() {
      this.$router.push('/family-request');
    },
    redirectToHistory() {
      this.$router.push('/history');
    },
    redirectToHomeManagement() {
      this.$router.push('/house-management');
    },
    redirectToHistoryEntries() {
      this.$router.push('/history-entries');
    },
    redirectoToDevices() {
      this.$router.push('/devices');
    },
    redirectToStateRequest() {
      this.$router.push('/state-request');
    },
    logout() {
      localStorage.removeItem('token');
      this.$router.push('/');
    }
  },
  mounted() {
    this.checkMobile();
    window.addEventListener('resize', this.checkMobile);
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.checkMobile);
  },
  created() {
    this.nameGoogle = localStorage.getItem('name')
    this.pictureGoogle = localStorage.getItem('picture')
  }
}
</script>

<style scoped>
.navbar {
  height: 99px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
}

.img-logo {
  width: 65px;
  height: 69px;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-left: auto;
  cursor: pointer;
  position: relative;
}

.user-dropdown {
  position: absolute;
  top: 100%;
  right: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: #F3F6F8;
  border: 1px solid #ccc;
  padding: 1em;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  border-radius: 20px;
  z-index: 1000;
}

.google-image-dropdown {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-bottom: 10px;
}

h4 {
  display: flex;
  align-items: center;
  color: #171616;
  font-size: 24px;
  padding: 15px;
}

.d-flex {
  display: flex;
  gap: 10px;
  flex-grow: 1;
  justify-content: center;
}

.google-image {
  width: 66px;
  height: 66px;
  border-radius: 50%;
}

.hamburger {
  display: none;
  flex-direction: column;
  cursor: pointer;
}

.bar {
  width: 35px;
  height: 5px;
  background-color: #12453B;
  margin: 4px 0;
  transition: 0.4s;
  border-radius: 12px;
}

.menu {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  position: fixed;
  top: -100%;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: #12453B;
  z-index: 1000;
  transition: top 0.5s ease;
}

.menu-open {
  top: 0;
}

.change-bar1 {
  transform: rotate(-45deg) translate(-9px, 6px);
}

.change-bar2 {
  opacity: 0;
}

.change-bar3 {
  transform: rotate(45deg) translate(-8px, -8px);
}

.close-menu {
  position: absolute;
  top: 30px;
  right: 30px;
  font-size: 24px;
  cursor: pointer;
  color: #ffffff;
}

@media (max-width: 768px) {
  .hamburger {
    display: flex;
  }

  .user-info, .d-flex {
    display: none;
  }

  .navbar {
    justify-content: space-between; 
  }
}

@media (max-height: 913px){
  .menu-open {
    padding-bottom: 150px;
  }
}
</style>