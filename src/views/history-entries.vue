<template>
    <div>
      <Navbar :admin="true" />
      <div class="d-flex justify-center">
        <h3 class="titles-style" style="text-align: center; margin-top: 2em;" >Historial de entradas</h3>
        </div>
      
 
        <div class="content-container">
      <h3 class="josefin-sans title" style="margin-bottom: 0.5em;">Gráficos</h3>
      <div class="charts-container">
        <div class="chart-item-bar">
          <h3 class="chart-title josefin-sans-light">Cantidad de entradas por día</h3>
          <Bar v-if="loaded" class="chart" :data="chartData" :options="chartOptions" />
        </div>
        <div class="chart-item" style="">
          <h3 class="chart-title josefin-sans-light">Entradas por punto de acceso</h3>
          <Pie v-if="loaded" class="chart" style="height: 248px;" :data="pieDataAccess" :options="pieOptions" />
        </div>
        <div class="chart-item chart-type" style="">
          <h3 class="chart-title josefin-sans-light">Entradas de visitas por tipo</h3>
          <Pie v-if="loaded" class="chart" style="height: 248px;" :data="pieDataVisit" :options="pieOptions" ref="pieChartVisit" />
        </div>
      </div>
  
        <h3 class="josefin-sans title" style="margin-bottom: 1em;">Todas las entradas</h3>
        
      </div>
      <v-data-table :headers="headers" :items="entries" class="entries-table" :page.sync="page" :items-per-page.sync="itemsPerPage"
       :server-items-lenght="totalEntries" @update:page="onPageChange" @update:items-per-page="onItemsPerPageChange"></v-data-table>
    </div>
  </template>
  
  <script>
  import Navbar from '../components/navbar.vue';
  import { Bar, Pie } from 'vue-chartjs';
  import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale, ArcElement } from 'chart.js'
  import services from '../services';
  
  ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale, ArcElement)
  
  export default {
    components: {
      Navbar,
      Bar,
      Pie
    },
    data() {
      return {
        headers: [
          { title: 'Nombre', value: 'userName' },
          { title: 'DUI', value: 'dui' },
          {title: 'Casa', value: 'house'},          
          { title: 'Fecha', value: 'date' },
          { title: 'Hora', value: 'entryTime' },
        ],
        entries: [],
        chartData: {
          labels: ['Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado', 'Domingo'],
          datasets: [
            {
              label: 'Cantidad de entradas por día',
              backgroundColor: '#12453B',
              data: [],
            },
          ],
        },
        pieDataAccess: {
          labels: ['Principal', 'Secundario'],
          datasets: [
            {
              label: 'Entradas por punto de acceso',
              backgroundColor: ['#FEAEAE', '#D0E8FF'],
              data: [],
            },
          ],
        },
        pieDataVisit: {
          labels: ['Residente', 'Visita', 'Anonima'],
          datasets: [
            {
              label: 'Entradas de visitas por tipo',
              backgroundColor: ['#FEAEAE', '#D0E8FF', '#A9F4D0'],
              data: [],
            },
          ],
        },
        chartOptions: {
          responsive: true,
          maintainAspectRatio: false,
        },
        pieOptions: {
          responsive: true,
          maintainAspectRatio: false,
        },
        loaded: false,
        itemsPerPage: 10,
        page: 1,
        totalEntries: 0,
      };
    },
    methods: {
      async getHistoryGraphics() {
        const getHistoryGraphics = await services.admin.getHistoryEntriesGraphics();
        const graph1Counts = getHistoryGraphics.data.data.graph1Counts;
        const graph2Counts = getHistoryGraphics.data.data.graph2Counts;
        const graph3Counts = getHistoryGraphics.data.data.graph3Counts;
        this.pieDataVisit.datasets[0].data = [...graph2Counts];
        this.pieDataAccess.datasets[0].data = [...graph1Counts];
        this.chartData.datasets[0].data = [...graph3Counts];
        this.loaded = true;     
      },
      async getAllHistoryEntries() {
        const getAllHistoryEntries = await services.admin.getAllHistoryEntries(this.page, this.itemsPerPage);
        this.entries = this.transformEntries(getAllHistoryEntries.data.data.content);
        this.totalEntries = getAllHistoryEntries.data.data.totalElements;
      },
      transformEntries(entries) {
      return entries.map(entry => {
        return {
          ...entry,
          house: entry.houseNumber ? `Casa # ${entry.houseNumber}` : 'Anonima'
        };
      });
    },
      onPageChange(newPage) {
      this.page = newPage;
      this.getAllHistoryEntries();
      },
      onItemsPerPageChange(newItemsPerPage) {
      this.itemsPerPage = newItemsPerPage;
      this.getAllHistoryEntries();
      }
    },
    created() {
      this.getHistoryGraphics();
      this.getAllHistoryEntries();
    }
  };
  </script>
  
  <style scoped>
  .content-container {
    padding: 1em;
    margin-left: 3em;
  }
  
  .title {
    color: #171616;
text-align: center;
font-feature-settings: 'clig' off, 'liga' off;
font-family: "Josefin Sans";
font-size: 24px;
font-style: normal;
font-weight: 400;
line-height: normal;
    text-align: left;
  }
  
  .charts-container {
    display: flex;
    flex-direction: row;
    margin-bottom: 3em;
    flex-wrap: wrap;
    gap: 2em;

  }
  
  .entries-table {
    background-color: #EDF1F4;
  }


.chart-item {
  min-width: 500px;
  max-width: 343px;
  flex-shrink: 0;
  height: 330px;
  background-color: #F6F9FB;
  border-radius: 30px;
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 1em;
  margin-left: 6em;
}

.chart-item-bar{
  width: 40%;
  min-width: 343px;
  flex-shrink: 0;
  height: 330px;
  background-color: #F6F9FB;
  border-radius: 30px;
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 1em;
}

.chart {
    height: 258px !important;
}

.chart-title {
  color: #000;

text-align: center;
font-feature-settings: 'clig' off, 'liga' off;
font-family: "Josefin Sans";
font-size: 20px;
font-style: normal;
font-weight: 300;
line-height: normal;
}

.chart-type {
  margin-left: 26em;
}
  
  .v-data-table {
    width: 100%;
  }
  
  .josefin-sans {
    font-family: 'Josefin Sans', sans-serif;
  }

  @media (max-width: 1024px ) {
    .chart-item {
      min-width: 90%;
      height: 330px;
      margin-left: 0;
    }

    .chart-item-bar{
      min-width: 92%;
      height: 340px;
    }

    .chart{
      width: 100%;
      height: 100%;
    
    }
    .content-container {
    padding: 1em;
    margin-left: 1em;
  }
}
  </style>
  