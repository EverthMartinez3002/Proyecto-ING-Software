<template>
  <div class="navbar">
    <img src="/src/assets/img/logo_HLVS.png" alt="logo_HLVS" class="img-logo">

    <div class="hamburger" @click="toggleMenu" v-if="isMobile">
      <div :class="{'bar': true, 'bar1': true, 'change-bar1': isMenuOpen}"></div>
      <div :class="{'bar': true, 'bar2': true, 'change-bar2': isMenuOpen}"></div>
      <div :class="{'bar': true, 'bar3': true, 'change-bar3': isMenuOpen}"></div>
    </div>

    <div class="d-flex" v-if="!isMobile">
      <h4 class="josefin-sans-light">Familia</h4>
      <h4 class="josefin-sans-light">Generar QR</h4>
      <h4 class="josefin-sans-light">Solicitudes</h4>
      <h4 class="josefin-sans-light">Historial</h4>
    </div>

    <div class="user-info" v-if="!isMobile">
      <h4 class="josefin-sans-light">Duglas Pineda</h4>
      <img src="/src/assets/img/google-image.png" alt="google_image" class="google-image">
    </div>
  </div>

  <!-- Menú móvil -->
  <div :class="{'menu': true, 'menu-open': isMenuOpen}">
    <div class="close-menu" @click="toggleMenu">X</div>
    <h4 style="color: #FFF;" class="josefin-sans-light" @click="toggleMenu">Familia</h4>
    <h4 style="color: #FFF;" class="josefin-sans-light" @click="toggleMenu">Generar QR</h4>
    <h4 style="color: #FFF;" class="josefin-sans-light" @click="toggleMenu">Solicitudes</h4>
    <h4 style="color: #FFF;" class="josefin-sans-light" @click="toggleMenu">Historial</h4>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isMenuOpen: false,
      isMobile: false
    }
  },
  methods: {
    toggleMenu() {
      this.isMenuOpen = !this.isMenuOpen;
    },
    checkMobile() {
      this.isMobile = window.innerWidth <= 768;
    }
  },
  mounted() {
    this.checkMobile();
    window.addEventListener('resize', this.checkMobile);
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.checkMobile);
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
  border-radius: 66px;
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
