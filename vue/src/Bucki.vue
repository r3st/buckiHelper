<template>
  <div>
    <Menubar :model="menuItems">
      <template #start>
        <img alt="logo" src="images/logo.png" height="40">
      </template>
      <template #end>
        <p>Hostname: {{ hostname }}</p>
      </template>
    </Menubar>
    <TabView>
      <TabPanel header="q/health">
        <HealthHelper ref="health" uri="q/health" @updateHealthHelper="updateHealthHelper()"/>
      </TabPanel>
      <TabPanel header="q/health/ready">
        <HealthHelper ref="healthReady" uri="q/health/ready" @updateHealthHelper="updateHealthHelper()"/>
      </TabPanel>
      <TabPanel header="q/health/live">
        <HealthHelper ref="healthLive" uri="q/health/live" @updateHealthHelper="updateHealthHelper()"/>
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
    const hostname = ref (String);

    return {
      menuItems,
      hostname,
    };
  },
  created() {
    this.menuItems = [
          {
            label:'reload',
            icon:'pi pi-fw pi-refresh',
            command: () => this.updateHealthHelper()
          }
        ];
    this.getHostname();
  },
  methods: {
    updateHealthHelper() {
      this.$refs.health.getHealth();
      this.$refs.healthReady.getHealth();
      this.$refs.healthLive.getHealth();
      },
    getHostname() {
      this.axios.get("bucki/hostname").then((resp) => {
        this.hostname = resp.data;
        }
      );
    }
  }
};
</script>

<style>
body {
  background-color: gray;
}
</style>
