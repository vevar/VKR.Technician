package com.nstu.technician.data.datasource.local

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    value = [AddressLocalSourceTest::class, ArtifactLocalSourceTest::class, ComponentLocalSourceTest::class,
        ComponentTypeLocalSourceTest::class, ComponentUnitLocalSourceTest::class,
        ContractLocalSourceTest::class, ContractorLocalSourceTest::class, FacilityLocalSourceTest::class, MaintenanceLocalSourceTest::class,
        ImplementsLocalSourceTest::class, ImplementUnitLocalSourceTest::class, MaintenanceJobLocalSourceTest::class,
        ShiftLocalSourceTest::class]
)
class SuitLocalSource