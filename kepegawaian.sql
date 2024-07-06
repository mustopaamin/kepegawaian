-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jul 06, 2024 at 11:13 PM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 7.4.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kepegawaian`
--

-- --------------------------------------------------------

--
-- Table structure for table `absensi_kehadiran`
--

CREATE TABLE `absensi_kehadiran` (
  `id` int(11) NOT NULL,
  `nip` varchar(100) NOT NULL,
  `tanggal` date NOT NULL,
  `kehadiran` varchar(25) NOT NULL,
  `keterangan` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `absensi_kehadiran`
--

INSERT INTO `absensi_kehadiran` (`id`, `nip`, `tanggal`, `kehadiran`, `keterangan`) VALUES
(1, '02150210', '2023-05-28', 'Hadir', 'Datang tepat waktu ya'),
(2, '02150211', '2023-05-28', 'Tidak Hadir', 'Sakit'),
(3, '02150212', '2023-05-28', 'Hadir', 'Datang tepat waktu'),
(4, '02150213', '2023-05-28', 'Hadir', ''),
(5, '06240609', '2023-05-28', 'Tidak Hadir', 'Cuti'),
(7, '02150211', '2024-06-04', 'Sakit', 'Sakit'),
(8, '02150210', '2023-06-01', 'Hadir', NULL),
(9, '02150211', '2023-06-01', 'Sakit', 'Flu'),
(10, '02150212', '2023-06-01', 'Izin', 'Acara keluarga'),
(11, '02150213', '2023-06-01', 'Cuti', 'Liburan'),
(12, '06240609', '2023-06-01', 'Tanpa Keterangan', NULL),
(13, '06240610', '2023-06-01', 'Hadir', NULL),
(14, '02150210', '2023-06-02', 'Sakit', 'Demam'),
(15, '02150211', '2023-06-02', 'Izin', 'Urusan pribadi'),
(16, '02150212', '2023-06-02', 'Cuti', 'Liburan keluarga'),
(17, '02150213', '2023-06-02', 'Tanpa Keterangan', NULL),
(18, '06240609', '2023-06-02', 'Hadir', NULL),
(19, '06240610', '2023-06-02', 'Sakit', 'Migrain'),
(20, '02150210', '2023-06-03', 'Izin', 'Acara pernikahan'),
(21, '02150211', '2023-06-03', 'Cuti', 'Liburan tahunan'),
(22, '02150212', '2023-06-03', 'Tanpa Keterangan', NULL),
(23, '02150213', '2023-06-03', 'Hadir', NULL),
(24, '06240609', '2023-06-03', 'Sakit', 'Demam tinggi'),
(25, '06240610', '2023-06-03', 'Izin', 'Keperluan mendesak'),
(26, '02150210', '2023-06-04', 'Cuti', 'Liburan ke luar kota'),
(27, '02150211', '2023-06-04', 'Tanpa Keterangan', NULL),
(28, '02150212', '2023-06-04', 'Hadir', NULL),
(29, '02150213', '2023-06-04', 'Sakit', 'Flu berat'),
(30, '06240609', '2023-06-04', 'Izin', 'Urusan keluarga'),
(31, '06240610', '2023-06-04', 'Cuti', 'Perjalanan dinas'),
(32, '02150210', '2023-06-05', 'Tanpa Keterangan', NULL),
(33, '02150211', '2023-06-05', 'Hadir', NULL),
(34, '02150212', '2023-06-05', 'Sakit', 'Infeksi'),
(35, '02150213', '2023-06-05', 'Izin', 'Acara keluarga'),
(36, '06240609', '2023-06-05', 'Cuti', 'Liburan bersama keluarga'),
(37, '06240610', '2023-06-05', 'Tanpa Keterangan', NULL),
(38, '02150210', '2023-06-06', 'Hadir', NULL),
(39, '02150211', '2023-06-06', 'Sakit', 'Flu'),
(40, '02150212', '2023-06-06', 'Izin', 'Acara keluarga'),
(41, '02150213', '2023-06-06', 'Cuti', 'Liburan'),
(42, '06240609', '2023-06-06', 'Tanpa Keterangan', NULL),
(43, '06240610', '2023-06-06', 'Hadir', NULL),
(44, '02150210', '2023-06-07', 'Sakit', 'Demam'),
(45, '02150211', '2023-06-07', 'Izin', 'Urusan pribadi'),
(46, '02150212', '2023-06-07', 'Cuti', 'Liburan keluarga'),
(47, '02150213', '2023-06-07', 'Tanpa Keterangan', NULL),
(48, '06240609', '2023-06-07', 'Hadir', NULL),
(49, '06240610', '2023-06-07', 'Sakit', 'Migrain'),
(134, '02150210', '2024-06-01', 'Hadir', NULL),
(135, '02150211', '2024-06-01', 'Sakit', 'Flu'),
(136, '02150212', '2024-06-01', 'Izin', 'Acara keluarga'),
(137, '02150213', '2024-06-01', 'Cuti', 'Liburan'),
(138, '06240609', '2024-06-01', 'Tanpa Keterangan', NULL),
(139, '06240610', '2024-06-01', 'Hadir', NULL),
(146, '02150210', '2024-06-03', 'Izin', 'Acara pernikahan'),
(147, '02150211', '2024-06-03', 'Cuti', 'Liburan tahunan'),
(148, '02150212', '2024-06-03', 'Tanpa Keterangan', NULL),
(149, '02150213', '2024-06-03', 'Hadir', NULL),
(150, '06240609', '2024-06-03', 'Sakit', 'Demam tinggi'),
(151, '06240610', '2024-06-03', 'Izin', 'Keperluan mendesak'),
(152, '02150210', '2024-06-04', 'Cuti', 'Liburan ke luar kota'),
(153, '02150211', '2024-06-04', 'Tanpa Keterangan', NULL),
(154, '02150212', '2024-06-04', 'Hadir', NULL),
(155, '02150213', '2024-06-04', 'Sakit', 'Flu berat'),
(156, '06240609', '2024-06-04', 'Izin', 'Urusan keluarga'),
(157, '06240610', '2024-06-04', 'Cuti', 'Perjalanan dinas'),
(158, '02150210', '2024-06-05', 'Tanpa Keterangan', NULL),
(159, '02150211', '2024-06-05', 'Hadir', NULL),
(160, '02150212', '2024-06-05', 'Sakit', 'Infeksi'),
(161, '02150213', '2024-06-05', 'Izin', 'Acara keluarga'),
(162, '06240609', '2024-06-05', 'Cuti', 'Liburan bersama keluarga'),
(163, '06240610', '2024-06-05', 'Tanpa Keterangan', NULL),
(164, '02150210', '2024-06-06', 'Hadir', NULL),
(165, '02150211', '2024-06-06', 'Sakit', 'Flu'),
(166, '02150212', '2024-06-06', 'Izin', 'Acara keluarga'),
(167, '02150213', '2024-06-06', 'Cuti', 'Liburan'),
(168, '06240609', '2024-06-06', 'Tanpa Keterangan', NULL),
(169, '06240610', '2024-06-06', 'Hadir', NULL),
(170, '02150210', '2024-06-07', 'Sakit', 'Demam'),
(171, '02150211', '2024-06-07', 'Izin', 'Urusan pribadi'),
(172, '02150212', '2024-06-07', 'Cuti', 'Liburan keluarga'),
(173, '02150213', '2024-06-07', 'Tanpa Keterangan', NULL),
(174, '06240609', '2024-06-07', 'Hadir', NULL),
(175, '06240610', '2024-06-07', 'Sakit', 'Migrain'),
(176, '02150210', '2024-06-08', 'Hadir', NULL),
(177, '02150211', '2024-06-08', 'Sakit', 'Flu'),
(178, '02150212', '2024-06-08', 'Izin', 'Acara keluarga'),
(179, '02150213', '2024-06-08', 'Cuti', 'Liburan'),
(180, '06240609', '2024-06-08', 'Tanpa Keterangan', NULL),
(181, '06240610', '2024-06-08', 'Hadir', NULL),
(188, '02150210', '2024-06-10', 'Izin', 'Acara pernikahan'),
(189, '02150211', '2024-06-10', 'Cuti', 'Liburan tahunan'),
(190, '02150212', '2024-06-10', 'Tanpa Keterangan', NULL),
(191, '02150213', '2024-06-10', 'Hadir', NULL),
(192, '06240609', '2024-06-10', 'Sakit', 'Demam tinggi'),
(193, '06240610', '2024-06-10', 'Izin', 'Keperluan mendesak'),
(194, '02150210', '2024-06-11', 'Cuti', 'Liburan ke luar kota'),
(195, '02150211', '2024-06-11', 'Tanpa Keterangan', NULL),
(196, '02150212', '2024-06-11', 'Hadir', NULL),
(197, '02150213', '2024-06-11', 'Sakit', 'Flu berat'),
(198, '06240609', '2024-06-11', 'Izin', 'Urusan keluarga'),
(199, '06240610', '2024-06-11', 'Cuti', 'Perjalanan dinas'),
(200, '02150210', '2024-06-12', 'Tanpa Keterangan', NULL),
(201, '02150211', '2024-06-12', 'Hadir', NULL),
(202, '02150212', '2024-06-12', 'Sakit', 'Infeksi'),
(203, '02150213', '2024-06-12', 'Izin', 'Acara keluarga'),
(204, '06240609', '2024-06-12', 'Cuti', 'Liburan bersama keluarga'),
(205, '06240610', '2024-06-12', 'Tanpa Keterangan', NULL),
(206, '02150210', '2024-06-13', 'Hadir', NULL),
(207, '02150211', '2024-06-13', 'Sakit', 'Flu'),
(208, '02150212', '2024-06-13', 'Izin', 'Acara keluarga'),
(209, '02150213', '2024-06-13', 'Cuti', 'Liburan'),
(210, '06240609', '2024-06-13', 'Tanpa Keterangan', NULL),
(211, '06240610', '2024-06-13', 'Hadir', NULL),
(212, '02150210', '2024-06-14', 'Sakit', 'Demam'),
(213, '02150211', '2024-06-14', 'Izin', 'Urusan pribadi'),
(214, '02150212', '2024-06-14', 'Cuti', 'Liburan keluarga'),
(215, '02150213', '2024-06-14', 'Tanpa Keterangan', NULL),
(216, '06240609', '2024-06-14', 'Hadir', NULL),
(217, '06240610', '2024-06-14', 'Sakit', 'Migrain'),
(218, '02150210', '2024-06-15', 'Hadir', NULL),
(219, '02150211', '2024-06-15', 'Sakit', 'Flu'),
(220, '02150212', '2024-06-15', 'Izin', 'Acara keluarga'),
(221, '02150213', '2024-06-15', 'Cuti', 'Liburan'),
(222, '06240609', '2024-06-15', 'Tanpa Keterangan', NULL),
(223, '06240610', '2024-06-15', 'Hadir', NULL),
(230, '02150210', '2024-06-17', 'Izin', 'Acara pernikahan'),
(231, '02150211', '2024-06-17', 'Cuti', 'Liburan tahunan'),
(232, '02150212', '2024-06-17', 'Tanpa Keterangan', NULL),
(233, '02150213', '2024-06-17', 'Hadir', NULL),
(234, '06240609', '2024-06-17', 'Sakit', 'Demam tinggi'),
(235, '06240610', '2024-06-17', 'Izin', 'Keperluan mendesak'),
(236, '02150210', '2024-06-18', 'Cuti', 'Liburan ke luar kota'),
(237, '02150211', '2024-06-18', 'Tanpa Keterangan', NULL),
(238, '02150212', '2024-06-18', 'Hadir', NULL),
(239, '02150213', '2024-06-18', 'Sakit', 'Flu berat'),
(240, '06240609', '2024-06-18', 'Izin', 'Urusan keluarga'),
(241, '06240610', '2024-06-18', 'Cuti', 'Perjalanan dinas'),
(242, '02150210', '2024-06-19', 'Tanpa Keterangan', NULL),
(243, '02150211', '2024-06-19', 'Hadir', NULL),
(244, '02150212', '2024-06-19', 'Sakit', 'Infeksi'),
(245, '02150213', '2024-06-19', 'Izin', 'Acara keluarga'),
(246, '06240609', '2024-06-19', 'Cuti', 'Liburan bersama keluarga'),
(247, '06240610', '2024-06-19', 'Tanpa Keterangan', NULL),
(248, '02150210', '2024-06-20', 'Hadir', NULL),
(249, '02150211', '2024-06-20', 'Sakit', 'Flu'),
(250, '02150212', '2024-06-20', 'Izin', 'Acara keluarga'),
(251, '02150213', '2024-06-20', 'Cuti', 'Liburan'),
(252, '06240609', '2024-06-20', 'Tanpa Keterangan', NULL),
(253, '06240610', '2024-06-20', 'Hadir', NULL),
(254, '02150210', '2024-06-21', 'Sakit', 'Demam'),
(255, '02150211', '2024-06-21', 'Izin', 'Urusan pribadi'),
(256, '02150212', '2024-06-21', 'Cuti', 'Liburan keluarga'),
(257, '02150213', '2024-06-21', 'Tanpa Keterangan', NULL),
(258, '06240609', '2024-06-21', 'Hadir', NULL),
(259, '06240610', '2024-06-21', 'Sakit', 'Migrain'),
(260, '02150210', '2024-06-22', 'Hadir', NULL),
(261, '02150211', '2024-06-22', 'Sakit', 'Flu'),
(262, '02150212', '2024-06-22', 'Izin', 'Acara keluarga'),
(263, '02150213', '2024-06-22', 'Cuti', 'Liburan'),
(264, '06240609', '2024-06-22', 'Tanpa Keterangan', NULL),
(265, '06240610', '2024-06-22', 'Hadir', NULL),
(272, '02150210', '2024-06-24', 'Izin', 'Acara pernikahan'),
(273, '02150211', '2024-06-24', 'Cuti', 'Liburan tahunan'),
(274, '02150212', '2024-06-24', 'Tanpa Keterangan', NULL),
(275, '02150213', '2024-06-24', 'Hadir', NULL),
(276, '06240609', '2024-06-24', 'Sakit', 'Demam tinggi'),
(277, '06240610', '2024-06-24', 'Izin', 'Keperluan mendesak'),
(278, '02150210', '2024-06-25', 'Cuti', 'Liburan ke luar kota'),
(279, '02150211', '2024-06-25', 'Tanpa Keterangan', NULL),
(280, '02150212', '2024-06-25', 'Hadir', NULL),
(281, '02150213', '2024-06-25', 'Sakit', 'Flu berat'),
(282, '06240609', '2024-06-25', 'Izin', 'Urusan keluarga'),
(283, '06240610', '2024-06-25', 'Cuti', 'Perjalanan dinas'),
(284, '02150210', '2024-06-26', 'Tanpa Keterangan', NULL),
(285, '02150211', '2024-06-26', 'Hadir', NULL),
(286, '02150212', '2024-06-26', 'Sakit', 'Infeksi'),
(287, '02150213', '2024-06-26', 'Izin', 'Acara keluarga'),
(288, '06240609', '2024-06-26', 'Cuti', 'Liburan bersama keluarga'),
(289, '06240610', '2024-06-26', 'Tanpa Keterangan', NULL),
(290, '02150210', '2024-06-27', 'Hadir', NULL),
(291, '02150211', '2024-06-27', 'Sakit', 'Flu'),
(292, '02150212', '2024-06-27', 'Izin', 'Acara keluarga'),
(293, '02150213', '2024-06-27', 'Cuti', 'Liburan'),
(294, '06240609', '2024-06-27', 'Tanpa Keterangan', NULL),
(295, '06240610', '2024-06-27', 'Hadir', NULL),
(296, '02150210', '2024-06-28', 'Sakit', 'Demam'),
(297, '02150211', '2024-06-28', 'Izin', 'Urusan pribadi'),
(298, '02150212', '2024-06-28', 'Cuti', 'Liburan keluarga'),
(299, '02150213', '2024-06-28', 'Tanpa Keterangan', NULL),
(300, '06240609', '2024-06-28', 'Hadir', NULL),
(301, '06240610', '2024-06-28', 'Sakit', 'Migrain'),
(302, '02150210', '2024-06-29', 'Hadir', NULL),
(303, '02150211', '2024-06-29', 'Sakit', 'Flu'),
(304, '02150212', '2024-06-29', 'Izin', 'Acara keluarga'),
(305, '02150213', '2024-06-29', 'Cuti', 'Liburan'),
(306, '06240609', '2024-06-29', 'Tanpa Keterangan', NULL),
(307, '06240610', '2024-06-29', 'Hadir', NULL),
(308, '02150210', '2024-07-01', 'Sakit', 'Demam'),
(309, '02150211', '2024-07-01', 'Izin', 'Urusan pribadi'),
(310, '02150212', '2024-07-01', 'Cuti', 'Liburan keluarga'),
(311, '02150213', '2024-07-01', 'Tanpa Keterangan', NULL),
(312, '06240609', '2024-07-01', 'Hadir', NULL),
(313, '06240610', '2024-07-01', 'Sakit', 'Migrain'),
(314, '02150210', '2024-07-02', 'Izin', 'Acara pernikahan'),
(315, '02150211', '2024-07-02', 'Cuti', 'Liburan tahunan'),
(316, '02150212', '2024-07-02', 'Tanpa Keterangan', NULL),
(317, '02150213', '2024-07-02', 'Hadir', NULL),
(318, '06240609', '2024-07-02', 'Sakit', 'Demam tinggi'),
(319, '06240610', '2024-07-02', 'Izin', 'Keperluan mendesak'),
(320, '02150210', '2024-07-03', 'Cuti', 'Liburan ke luar kota'),
(321, '02150211', '2024-07-03', 'Tanpa Keterangan', NULL),
(322, '02150212', '2024-07-03', 'Hadir', NULL),
(323, '02150213', '2024-07-03', 'Sakit', 'Flu berat'),
(324, '06240609', '2024-07-03', 'Izin', 'Urusan keluarga'),
(325, '06240610', '2024-07-03', 'Cuti', 'Perjalanan dinas'),
(326, '02150210', '2024-07-04', 'Tanpa Keterangan', NULL),
(327, '02150211', '2024-07-04', 'Hadir', NULL),
(328, '02150212', '2024-07-04', 'Sakit', 'Infeksi'),
(329, '02150213', '2024-07-04', 'Izin', 'Acara keluarga'),
(330, '06240609', '2024-07-04', 'Cuti', 'Liburan bersama keluarga'),
(332, '02150210', '2024-07-05', 'Hadir', NULL),
(333, '02150211', '2024-07-05', 'Sakit', 'Flu'),
(334, '02150212', '2024-07-05', 'Izin', 'Acara keluarga'),
(335, '02150213', '2024-07-05', 'Cuti', 'Liburan'),
(336, '06240609', '2024-07-05', 'Tanpa Keterangan', NULL),
(337, '06240610', '2024-07-05', 'Hadir', NULL),
(338, '02150210', '2024-07-06', 'Hadir', NULL),
(339, '02150211', '2024-07-06', 'Sakit', 'Flu'),
(340, '02150212', '2024-07-06', 'Izin', 'Acara keluarga'),
(341, '02150213', '2024-07-06', 'Cuti', 'Liburan'),
(342, '06240609', '2024-07-06', 'Tanpa Keterangan', NULL),
(343, '06240610', '2024-07-06', 'Hadir', NULL),
(344, '02150210', '2024-07-08', 'Sakit', 'Demam'),
(345, '02150211', '2024-07-08', 'Izin', 'Urusan pribadi'),
(346, '02150212', '2024-07-08', 'Cuti', 'Liburan keluarga'),
(347, '02150213', '2024-07-08', 'Tanpa Keterangan', NULL),
(348, '06240609', '2024-07-08', 'Hadir', NULL),
(349, '06240610', '2024-07-08', 'Sakit', 'Migrain'),
(350, '02150210', '2024-07-09', 'Hadir', NULL),
(351, '02150211', '2024-07-09', 'Sakit', 'Flu'),
(352, '02150212', '2024-07-09', 'Izin', 'Acara keluarga'),
(353, '02150213', '2024-07-09', 'Cuti', 'Liburan'),
(354, '06240609', '2024-07-09', 'Tanpa Keterangan', NULL),
(355, '06240610', '2024-07-09', 'Hadir', NULL),
(356, '02150210', '2024-07-10', 'Sakit', 'Demam'),
(357, '02150211', '2024-07-10', 'Izin', 'Urusan pribadi'),
(358, '02150212', '2024-07-10', 'Cuti', 'Liburan keluarga'),
(359, '02150213', '2024-07-10', 'Tanpa Keterangan', NULL),
(360, '06240609', '2024-07-10', 'Hadir', NULL),
(361, '06240610', '2024-07-10', 'Sakit', 'Migrain'),
(362, '02150210', '2024-07-11', 'Izin', 'Acara pernikahan'),
(363, '02150211', '2024-07-11', 'Cuti', 'Liburan tahunan'),
(364, '02150212', '2024-07-11', 'Tanpa Keterangan', NULL),
(365, '02150213', '2024-07-11', 'Hadir', NULL),
(366, '06240609', '2024-07-11', 'Sakit', 'Demam tinggi'),
(367, '06240610', '2024-07-11', 'Izin', 'Keperluan mendesak'),
(368, '02150210', '2024-07-12', 'Cuti', 'Liburan ke luar kota'),
(369, '02150211', '2024-07-12', 'Tanpa Keterangan', NULL),
(370, '02150212', '2024-07-12', 'Hadir', NULL),
(371, '02150213', '2024-07-12', 'Sakit', 'Flu berat'),
(372, '06240609', '2024-07-12', 'Izin', 'Urusan keluarga'),
(373, '06240610', '2024-07-12', 'Cuti', 'Perjalanan dinas'),
(374, '02150210', '2024-07-13', 'Tanpa Keterangan', NULL),
(375, '02150211', '2024-07-13', 'Hadir', NULL),
(376, '02150212', '2024-07-13', 'Sakit', 'Infeksi'),
(377, '02150213', '2024-07-13', 'Izin', 'Acara keluarga'),
(378, '06240609', '2024-07-13', 'Cuti', 'Liburan bersama keluarga'),
(379, '06240610', '2024-07-13', 'Tanpa Keterangan', NULL),
(385, '06240610', '2024-07-03', 'Cuti', 'Antar Orang Tua'),
(386, '07240701', '2024-07-04', 'Hadir', '-');

-- --------------------------------------------------------

--
-- Table structure for table `daftar_gaji`
--

CREATE TABLE `daftar_gaji` (
  `id` int(11) NOT NULL,
  `nip` varchar(100) NOT NULL,
  `bulan` int(11) NOT NULL CHECK (`bulan` between 1 and 12),
  `tahun` int(11) NOT NULL,
  `gaji_pokok` decimal(15,2) NOT NULL DEFAULT 0.00,
  `tunjangan` int(11) NOT NULL DEFAULT 0,
  `pinjaman` int(11) NOT NULL DEFAULT 0,
  `gaji_bersih` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `daftar_gaji`
--

INSERT INTO `daftar_gaji` (`id`, `nip`, `bulan`, `tahun`, `gaji_pokok`, `tunjangan`, `pinjaman`, `gaji_bersih`) VALUES
(1, '02150210', 5, 2024, 5000000.00, 0, 0, 0),
(3, '02150212', 5, 2024, 4800000.00, 0, 0, 0),
(4, '02150213', 5, 2024, 5200000.00, 0, 0, 0),
(5, '06240609', 5, 2024, 4700000.00, 0, 0, 0),
(6, '02150211', 5, 2024, 5000000.00, 250000, 75000, 5175000),
(7, '02150210', 5, 2024, 5000000.00, 0, 0, 5000000),
(8, '02150212', 5, 2024, 4800000.00, 0, 0, 4800000),
(9, '02150213', 5, 2024, 5200000.00, 0, 0, 5200000),
(10, '06240609', 5, 2024, 4700000.00, 0, 0, 4700000),
(11, '02150211', 5, 2024, 5000000.00, 250000, 75000, 5175000),
(12, '06240610', 5, 2024, 7500000.00, 0, 0, 7500000),
(13, '02150210', 7, 2024, 5000000.00, 0, 45000, 4955000),
(14, '02150211', 7, 2024, 5000000.00, 250000, 0, 5250000),
(15, '02150212', 7, 2024, 4800000.00, 0, 100000, 4700000),
(16, '02150213', 7, 2024, 5200000.00, 0, 75000, 5125000),
(17, '06240609', 7, 2024, 4700000.00, 0, 60000, 4640000),
(18, '06240610', 7, 2024, 7500000.00, 0, 85000, 7415000);

-- --------------------------------------------------------

--
-- Table structure for table `data_pegawai`
--

CREATE TABLE `data_pegawai` (
  `id` int(11) NOT NULL,
  `nip` varchar(25) NOT NULL,
  `nama_lengkap` varchar(100) NOT NULL,
  `tempat_lahir` varchar(100) NOT NULL,
  `tanggal_lahir` date NOT NULL,
  `jenis_kelamin` enum('Laki-laki','Perempuan') NOT NULL,
  `status_kepegawaian` varchar(50) NOT NULL,
  `jabatan` varchar(100) NOT NULL,
  `gaji` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `data_pegawai`
--

INSERT INTO `data_pegawai` (`id`, `nip`, `nama_lengkap`, `tempat_lahir`, `tanggal_lahir`, `jenis_kelamin`, `status_kepegawaian`, `jabatan`, `gaji`) VALUES
(1, '02150210', 'Budi Santosa', 'Jakarta', '1985-08-15', 'Laki-laki', 'Pegawai Tetap', 'Manager', 5000000),
(2, '02150211', 'Siti Aminah', 'Surabaya', '1990-11-22', 'Perempuan', 'Pegawai Kontrak', 'Staff', 5000000),
(3, '02150212', 'Ahmad Fauzi', 'Bandung', '1983-02-10', 'Laki-laki', 'Pegawai Tetap', 'Supervisor', 5000000),
(4, '02150213', 'Dewi Lestari', 'Yogyakarta', '1995-05-30', 'Perempuan', 'Pegawai Magang', 'Asisten', 5000000),
(5, '06240609', 'Rina Agustina', 'Medan', '1987-07-19', 'Perempuan', 'Pegawai Tetap', 'HRD', 5000000),
(6, '06240610', 'Mustopa Amin', 'Bogor', '2000-08-10', 'Laki-laki', 'Pegawai Tetap', 'Supervisor', 7500000),
(7, '06240611', 'Irwan Saputra', 'Malang', '1992-03-15', 'Laki-laki', 'Pegawai Tetap', 'Manager', 6000000),
(8, '06240612', 'Sri Rahayu', 'Semarang', '1989-10-05', 'Perempuan', 'Pegawai Kontrak', 'Staff', 4500000),
(9, '06240613', 'Joko Santoso', 'Solo', '1984-01-12', 'Laki-laki', 'Pegawai Tetap', 'Supervisor', 7000000),
(10, '06240614', 'Diana Puspita', 'Palembang', '1993-06-25', 'Perempuan', 'Pegawai Magang', 'Asisten', 3000000),
(11, '06240615', 'Rizky Hidayat', 'Makassar', '1996-09-30', 'Laki-laki', 'Pegawai Tetap', 'HRD', 6500000),
(12, '06240616', 'Lina Marlina', 'Padang', '1991-04-22', 'Perempuan', 'Pegawai Tetap', 'Staff', 5500000),
(13, '06240617', 'Bambang Irawan', 'Cirebon', '1982-12-18', 'Laki-laki', 'Pegawai Tetap', 'Supervisor', 7500000),
(14, '06240618', 'Yuni Astuti', 'Denpasar', '1986-07-02', 'Perempuan', 'Pegawai Tetap', 'Manager', 8000000),
(15, '06240619', 'Dian Sastrowardoyo', 'Tangerang', '1988-02-28', 'Perempuan', 'Pegawai Kontrak', 'Staff', 4800000),
(16, '06240620', 'Asep Sunarya', 'Bekasi', '1990-11-05', 'Laki-laki', 'Pegawai Tetap', 'Supervisor', 7300000),
(17, '06240621', 'Rudi Hartono', 'Depok', '1994-08-15', 'Laki-laki', 'Pegawai Magang', 'Asisten', 3200000),
(18, '06240622', 'Eka Putri', 'Serang', '1987-01-20', 'Perempuan', 'Pegawai Tetap', 'HRD', 6800000),
(19, '06240623', 'Hendra Setiawan', 'Pontianak', '1992-05-11', 'Laki-laki', 'Pegawai Tetap', 'Manager', 8500000),
(20, '06240624', 'Susi Susanti', 'Batam', '1985-09-09', 'Perempuan', 'Pegawai Kontrak', 'Staff', 4700000),
(21, '06240625', 'Agus Salim', 'Balikpapan', '1981-03-05', 'Laki-laki', 'Pegawai Tetap', 'Supervisor', 7400000),
(22, '06240626', 'Tina Tantri', 'Pekanbaru', '1993-12-18', 'Perempuan', 'Pegawai Tetap', 'HRD', 6600000),
(24, '07240701', 'Jaka S', 'Bogor', '2000-07-20', 'Laki-laki', 'Pegawai Kontrak', 'Staff', 6000000);

-- --------------------------------------------------------

--
-- Table structure for table `data_pinjaman`
--

CREATE TABLE `data_pinjaman` (
  `id` int(11) NOT NULL,
  `nip` varchar(100) NOT NULL,
  `tanggal` date NOT NULL,
  `nominal` int(11) NOT NULL DEFAULT 0,
  `keterangan` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `data_pinjaman`
--

INSERT INTO `data_pinjaman` (`id`, `nip`, `tanggal`, `nominal`, `keterangan`) VALUES
(1, '02150211', '2024-06-03', 50000, 'Bensin'),
(2, '02150211', '2024-06-07', 25000, 'Makan'),
(3, '02150210', '2024-04-05', 75000, 'Transportasi'),
(4, '02150212', '2024-04-12', 100000, 'Kesehatan'),
(5, '02150213', '2024-04-15', 60000, 'Makan siang'),
(6, '06240609', '2024-04-20', 45000, 'Bensin'),
(7, '06240610', '2024-04-25', 120000, 'Belanja Bulanan'),
(8, '02150210', '2024-05-03', 85000, 'Transportasi'),
(9, '02150211', '2024-05-07', 30000, 'Makan'),
(10, '02150212', '2024-05-11', 50000, 'Kesehatan'),
(11, '02150213', '2024-05-17', 70000, 'Transportasi'),
(12, '06240609', '2024-05-22', 45000, 'Bensin'),
(13, '02150211', '2024-06-03', 50000, 'Bensin'),
(14, '02150211', '2024-06-07', 25000, 'Makan'),
(15, '02150210', '2024-06-10', 60000, 'Makan siang'),
(16, '02150212', '2024-06-15', 80000, 'Belanja Bulanan'),
(17, '02150213', '2024-06-20', 70000, 'Kesehatan'),
(18, '06240609', '2024-06-25', 90000, 'Transportasi'),
(19, '02150210', '2024-07-02', 45000, 'Bensin'),
(20, '02150212', '2024-07-05', 100000, 'Belanja Bulanan'),
(21, '02150213', '2024-07-10', 75000, 'Makan siang'),
(22, '06240609', '2024-07-15', 60000, 'Transportasi'),
(23, '06240610', '2024-07-20', 85000, 'Kesehatan');

-- --------------------------------------------------------

--
-- Table structure for table `jabatan`
--

CREATE TABLE `jabatan` (
  `code` varchar(10) NOT NULL,
  `nama_jabatan` varchar(50) NOT NULL,
  `aktif` varchar(11) NOT NULL DEFAULT 'Aktif',
  `tgl_buat` timestamp NOT NULL DEFAULT current_timestamp(),
  `id_buat` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `jabatan`
--

INSERT INTO `jabatan` (`code`, `nama_jabatan`, `aktif`, `tgl_buat`, `id_buat`) VALUES
('JB001', 'Manager', 'Aktif', '2024-05-01 03:00:00', 1),
('JB002', 'Supervisor', 'Aktif', '2024-05-02 04:00:00', 2),
('JB003', 'Staff', 'Aktif', '2024-05-03 05:00:00', 3),
('JB004', 'Intern', 'Aktif', '2024-05-04 06:00:00', 4),
('JB005', 'Director', 'Aktif', '2024-05-05 07:00:00', 5),
('JB006', 'Asisten', 'Aktif', '2024-05-05 07:00:00', 5),
('JB007', 'HRD', 'Aktif', '2024-05-05 07:00:00', 5),
('JB008', 'OB', 'Aktif', '2024-07-02 11:25:04', NULL),
('JB009', 'Freelance', 'Aktif', '2024-07-05 15:06:37', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `riwayat_pendidikan`
--

CREATE TABLE `riwayat_pendidikan` (
  `id` int(11) NOT NULL,
  `nip` varchar(100) NOT NULL,
  `jenjang` varchar(50) NOT NULL,
  `tahun_masuk` int(11) NOT NULL,
  `tahun_lulus` int(11) NOT NULL,
  `gelar` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `riwayat_pendidikan`
--

INSERT INTO `riwayat_pendidikan` (`id`, `nip`, `jenjang`, `tahun_masuk`, `tahun_lulus`, `gelar`) VALUES
(1, '02150210', 'S1', 2010, 2014, 'Sarjana Teknik'),
(2, '02150211', 'S1', 2011, 2015, 'Sarjana Ekonomi'),
(3, '02150212', 'D3', 2012, 2015, 'Ahli Madya'),
(4, '02150213', 'S2', 2015, 2017, 'Magister Manajemen'),
(5, '06240609', 'S1', 2013, 2017, 'Sarjana Hukum'),
(6, '06240610', 'S1', 2016, 2020, 'Sarjana Teknik Informatika'),
(7, '06240611', 'S2', 2012, 2014, 'Magister Ekonomi'),
(8, '06240612', 'D3', 2009, 2012, 'Ahli Madya'),
(9, '06240613', 'S1', 2010, 2014, 'Sarjana Pertanian'),
(10, '06240614', 'S2', 2014, 2016, 'Magister Hukum'),
(11, '06240615', 'S1', 2011, 2015, 'Sarjana Psikologi'),
(12, '06240616', 'D3', 2010, 2013, 'Ahli Madya'),
(13, '06240617', 'S1', 2007, 2011, 'Sarjana Teknik Sipil'),
(14, '06240618', 'S2', 2013, 2015, 'Magister Manajemen'),
(15, '06240619', 'S1', 2008, 2012, 'Sarjana Akuntansi'),
(16, '06240620', 'S1', 2009, 2013, 'Sarjana Pendidikan'),
(17, '06240621', 'D3', 2010, 2013, 'Ahli Madya'),
(18, '06240622', 'S1', 2007, 2011, 'Sarjana Teknik Industri'),
(19, '06240623', 'S2', 2011, 2013, 'Magister Teknik'),
(20, '06240624', 'S1', 2008, 2012, 'Sarjana Komunikasi'),
(21, '06240625', 'S1', 2007, 2011, 'Sarjana Administrasi Bisnis'),
(22, '06240626', 'S1', 2009, 2013, 'Sarjana Sastra');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `nama_lengkap` varchar(100) NOT NULL,
  `role` enum('admin','pegawai') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `nama_lengkap`, `role`) VALUES
(1, 'admin', '12345', 'Administrator', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `absensi_kehadiran`
--
ALTER TABLE `absensi_kehadiran`
  ADD PRIMARY KEY (`id`),
  ADD KEY `nip` (`nip`);

--
-- Indexes for table `daftar_gaji`
--
ALTER TABLE `daftar_gaji`
  ADD PRIMARY KEY (`id`),
  ADD KEY `nip` (`nip`);

--
-- Indexes for table `data_pegawai`
--
ALTER TABLE `data_pegawai`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nip` (`nip`);

--
-- Indexes for table `data_pinjaman`
--
ALTER TABLE `data_pinjaman`
  ADD PRIMARY KEY (`id`),
  ADD KEY `nip` (`nip`);

--
-- Indexes for table `jabatan`
--
ALTER TABLE `jabatan`
  ADD PRIMARY KEY (`code`);

--
-- Indexes for table `riwayat_pendidikan`
--
ALTER TABLE `riwayat_pendidikan`
  ADD PRIMARY KEY (`id`),
  ADD KEY `nip` (`nip`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `absensi_kehadiran`
--
ALTER TABLE `absensi_kehadiran`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=388;

--
-- AUTO_INCREMENT for table `daftar_gaji`
--
ALTER TABLE `daftar_gaji`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `data_pegawai`
--
ALTER TABLE `data_pegawai`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `data_pinjaman`
--
ALTER TABLE `data_pinjaman`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `riwayat_pendidikan`
--
ALTER TABLE `riwayat_pendidikan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
