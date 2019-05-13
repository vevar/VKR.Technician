package com.nstu.technician.data.datasource.cloud

import org.junit.runner.RunWith
import org.junit.runners.Suite


@RunWith(Suite::class)
@Suite.SuiteClasses(
    value = [
        ArtifactCloudSourceTest::class,
        ComponentCloudSourceTest::class,
        ComponentTypeCloudSourceTest::class,
        ContractCloudSourceTest::class,
        ContractorCloudSourceTest::class,
        FacilityCloudSourceTest::class,
        FileCloudSourceTest::class,
        ImplementsCloudSourceTest::class,
        MaintenanceCloudSourceTest::class,
        ShiftCloudSourceTest::class,
        TechnicianCloudSourceTest::class,
        UserCloudSourceTest::class]
)
class SuitCloudSource