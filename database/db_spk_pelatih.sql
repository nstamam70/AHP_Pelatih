-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jul 21, 2026 at 11:23 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_spk_pelatih`
--

-- --------------------------------------------------------

--
-- Table structure for table `hasil`
--

CREATE TABLE `hasil` (
  `id_hasil` int(11) NOT NULL,
  `id_pelatih` int(11) DEFAULT NULL,
  `nilai_akhir` double DEFAULT NULL,
  `ranking` int(11) DEFAULT NULL,
  `tanggal` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `kriteria`
--

CREATE TABLE `kriteria` (
  `id_kriteria` int(11) NOT NULL,
  `kode_kriteria` varchar(10) NOT NULL,
  `nama_kriteria` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kriteria`
--

INSERT INTO `kriteria` (`id_kriteria`, `kode_kriteria`, `nama_kriteria`) VALUES
(1, 'KRT-001', 'Kompetensi'),
(2, 'KRT-002', 'Kedisiplinan'),
(6, 'KRT-003', 'Pembinaan');

-- --------------------------------------------------------

--
-- Table structure for table `pairwise_alternatif`
--

CREATE TABLE `pairwise_alternatif` (
  `id_pairwise` int(11) NOT NULL,
  `id_sub` int(11) NOT NULL,
  `id_pelatih1` int(11) NOT NULL,
  `id_pelatih2` int(11) NOT NULL,
  `nilai` decimal(10,4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `pairwise_kriteria`
--

CREATE TABLE `pairwise_kriteria` (
  `id_pairwise` int(11) NOT NULL,
  `id_sub1` int(11) DEFAULT NULL,
  `id_sub2` int(11) DEFAULT NULL,
  `nilai` decimal(10,4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `pelatih`
--

CREATE TABLE `pelatih` (
  `id_pelatih` int(11) NOT NULL,
  `kode_pelatih` varchar(10) NOT NULL,
  `nama_pelatih` varchar(100) NOT NULL,
  `alamat` text DEFAULT NULL,
  `no_hp` varchar(20) DEFAULT NULL,
  `lisensi` varchar(50) DEFAULT NULL,
  `status` enum('Aktif','Nonaktif') DEFAULT 'Aktif'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pelatih`
--

INSERT INTO `pelatih` (`id_pelatih`, `kode_pelatih`, `nama_pelatih`, `alamat`, `no_hp`, `lisensi`, `status`) VALUES
(1, 'PLT001', 'Ahmad Rivai', 'Depk', '081212828', 'Sertifikasi Garuda', 'Aktif');

-- --------------------------------------------------------

--
-- Table structure for table `penilaian`
--

CREATE TABLE `penilaian` (
  `id_penilaian` int(11) NOT NULL,
  `id_pelatih` int(11) DEFAULT NULL,
  `id_kriteria` int(11) DEFAULT NULL,
  `id_sub` int(11) DEFAULT NULL,
  `nilai` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `sub_kriteria`
--

CREATE TABLE `sub_kriteria` (
  `id_sub` int(11) NOT NULL,
  `id_kriteria` int(11) NOT NULL,
  `kode_sub` varchar(10) DEFAULT NULL,
  `nama_sub` varchar(100) DEFAULT NULL,
  `bobot` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sub_kriteria`
--

INSERT INTO `sub_kriteria` (`id_sub`, `id_kriteria`, `kode_sub`, `nama_sub`, `bobot`) VALUES
(1, 1, 'SK-001', 'Kompetensi Teknis', 0),
(2, 1, 'SK-002', 'Kepelatihan', 0),
(3, 1, 'SK-003', 'Kemampuan Komunikasi', 0),
(4, 2, 'SK-004', 'Kedisiplinan', 0),
(5, 2, 'SK-005', 'Tanggung Jawab Administratif', 0),
(6, 2, 'SK-006', 'Kepemimpinan', 0),
(10, 2, 'SK-010', 'TESTER', 20);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('Admin','Manager') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id_user`, `nama`, `username`, `password`, `role`) VALUES
(1, 'admin', 'admin', 'admin', 'Admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `hasil`
--
ALTER TABLE `hasil`
  ADD PRIMARY KEY (`id_hasil`),
  ADD KEY `id_pelatih` (`id_pelatih`);

--
-- Indexes for table `kriteria`
--
ALTER TABLE `kriteria`
  ADD PRIMARY KEY (`id_kriteria`),
  ADD UNIQUE KEY `kode_kriteria` (`kode_kriteria`);

--
-- Indexes for table `pairwise_alternatif`
--
ALTER TABLE `pairwise_alternatif`
  ADD PRIMARY KEY (`id_pairwise`),
  ADD UNIQUE KEY `unique_comparison` (`id_sub`,`id_pelatih1`,`id_pelatih2`),
  ADD KEY `id_sub` (`id_sub`),
  ADD KEY `id_pelatih1` (`id_pelatih1`),
  ADD KEY `id_pelatih2` (`id_pelatih2`);

--
-- Indexes for table `pairwise_kriteria`
--
ALTER TABLE `pairwise_kriteria`
  ADD PRIMARY KEY (`id_pairwise`),
  ADD UNIQUE KEY `unique_pair` (`id_sub1`,`id_sub2`),
  ADD KEY `id_sub1` (`id_sub1`),
  ADD KEY `id_sub2` (`id_sub2`);

--
-- Indexes for table `pelatih`
--
ALTER TABLE `pelatih`
  ADD PRIMARY KEY (`id_pelatih`),
  ADD UNIQUE KEY `kode_pelatih` (`kode_pelatih`);

--
-- Indexes for table `penilaian`
--
ALTER TABLE `penilaian`
  ADD PRIMARY KEY (`id_penilaian`),
  ADD KEY `id_pelatih` (`id_pelatih`),
  ADD KEY `id_sub` (`id_sub`),
  ADD KEY `fk_penilaian_kriteria` (`id_kriteria`);

--
-- Indexes for table `sub_kriteria`
--
ALTER TABLE `sub_kriteria`
  ADD PRIMARY KEY (`id_sub`),
  ADD KEY `id_kriteria` (`id_kriteria`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `hasil`
--
ALTER TABLE `hasil`
  MODIFY `id_hasil` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `kriteria`
--
ALTER TABLE `kriteria`
  MODIFY `id_kriteria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `pairwise_alternatif`
--
ALTER TABLE `pairwise_alternatif`
  MODIFY `id_pairwise` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pairwise_kriteria`
--
ALTER TABLE `pairwise_kriteria`
  MODIFY `id_pairwise` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pelatih`
--
ALTER TABLE `pelatih`
  MODIFY `id_pelatih` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `penilaian`
--
ALTER TABLE `penilaian`
  MODIFY `id_penilaian` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sub_kriteria`
--
ALTER TABLE `sub_kriteria`
  MODIFY `id_sub` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `hasil`
--
ALTER TABLE `hasil`
  ADD CONSTRAINT `hasil_ibfk_1` FOREIGN KEY (`id_pelatih`) REFERENCES `pelatih` (`id_pelatih`);

--
-- Constraints for table `pairwise_alternatif`
--
ALTER TABLE `pairwise_alternatif`
  ADD CONSTRAINT `pairwise_alt_ibfk_1` FOREIGN KEY (`id_sub`) REFERENCES `sub_kriteria` (`id_sub`) ON DELETE CASCADE,
  ADD CONSTRAINT `pairwise_alt_ibfk_2` FOREIGN KEY (`id_pelatih1`) REFERENCES `pelatih` (`id_pelatih`) ON DELETE CASCADE,
  ADD CONSTRAINT `pairwise_alt_ibfk_3` FOREIGN KEY (`id_pelatih2`) REFERENCES `pelatih` (`id_pelatih`) ON DELETE CASCADE;

--
-- Constraints for table `pairwise_kriteria`
--
ALTER TABLE `pairwise_kriteria`
  ADD CONSTRAINT `pairwise_kriteria_ibfk_1` FOREIGN KEY (`id_sub1`) REFERENCES `sub_kriteria` (`id_sub`) ON DELETE CASCADE,
  ADD CONSTRAINT `pairwise_kriteria_ibfk_2` FOREIGN KEY (`id_sub2`) REFERENCES `sub_kriteria` (`id_sub`) ON DELETE CASCADE;

--
-- Constraints for table `penilaian`
--
ALTER TABLE `penilaian`
  ADD CONSTRAINT `fk_penilaian_kriteria` FOREIGN KEY (`id_kriteria`) REFERENCES `kriteria` (`id_kriteria`) ON UPDATE CASCADE,
  ADD CONSTRAINT `penilaian_ibfk_1` FOREIGN KEY (`id_pelatih`) REFERENCES `pelatih` (`id_pelatih`),
  ADD CONSTRAINT `penilaian_ibfk_2` FOREIGN KEY (`id_sub`) REFERENCES `sub_kriteria` (`id_sub`);

--
-- Constraints for table `sub_kriteria`
--
ALTER TABLE `sub_kriteria`
  ADD CONSTRAINT `sub_kriteria_ibfk_1` FOREIGN KEY (`id_kriteria`) REFERENCES `kriteria` (`id_kriteria`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
