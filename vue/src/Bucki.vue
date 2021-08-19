<template>
  <div>
    <Menubar :model="menuItems">
      <template #start>
        <img alt="logo" src="images/logo.png" height="40">
      </template>
    </Menubar>
    <TabView>
      <TabPanel header="q/health">
        <HealthHelper uri="q/health"/>
      </TabPanel>
      <TabPanel header="q/health/ready">
        <HealthHelper uri="q/health/ready"/>
      </TabPanel>
      <TabPanel header="q/health/live">
        <HealthHelper uri="q/health/live"/>
      </TabPanel>
    </TabView>   
  </div>
</template>

<script>
import Menubar from "primevue/menubar";
import TabView from 'primevue/tabview';
import TabPanel from 'primevue/tabpanel';
import HealthHelper from "./components/healthHelper.vue"


import { ref } from "vue";

export default {
  name: "bucki",
  components: {
    Menubar,
    TabView,
    TabPanel,
    HealthHelper,
  },
  setup() {
    const menuItems = ref ([]);

    return {
      menuItems,
    };
  },
  created() {
    this.menuItems = [
          {
            label:'reload',
            icon:'pi pi-fw pi-refresh',
            //command: () => this.getHealth()
          }
        ];  
  }
};
</script>

<style>
body {
  background-color: gray;
}
</style>
